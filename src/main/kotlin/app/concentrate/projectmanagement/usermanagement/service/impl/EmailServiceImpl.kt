package app.concentrate.projectmanagement.usermanagement.service.impl

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.AppException
import app.concentrate.projectmanagement.usermanagement.constant.OTPConstant
import app.concentrate.projectmanagement.usermanagement.entity.OTPVerification
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.repository.OTPVerificationRepository
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import app.concentrate.projectmanagement.usermanagement.service.EmailService
import app.concentrate.projectmanagement.usermanagement.templet.EmailTemplate
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Random

@Service
@RequiredArgsConstructor
class EmailServiceImpl(
    private val javaMailSender: JavaMailSender,
    private val otpVerificationRepository: OTPVerificationRepository,
    private val userRepository: UserRepository,
//    private val profileService: ProfileService
) : EmailService {

    @Value("\${spring.mail.username}")
    private lateinit var email: String

    @Transactional
    @Async
    @Throws(MessagingException::class)
    override fun sendOTP(recipient: String) {
        otpVerificationRepository.findByEmail(recipient).ifPresent {
            otpVerificationRepository.deleteByEmail(recipient)
        }

        val otp = generateOTPEmail()
        val verification = OTPVerification(
            email = recipient,
            otp = otp,
            expiryTime = LocalDateTime.now().plusMinutes(15)
        )

        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val html = EmailTemplate.VERIFICATION_CODE_EMAIL.getBody(otp)

        helper.setTo(recipient)
        helper.setSubject(EmailTemplate.VERIFICATION_CODE_EMAIL.getSubject())
        helper.setText(html, true)
        helper.setFrom(email)

        javaMailSender.send(message)
        otpVerificationRepository.save(verification)
    }

    @Transactional
    override fun verifyOtp(recipient: String, otp: String): Boolean {
        val otpVerification = otpVerificationRepository.findByEmail(recipient)
            .orElseThrow { AppException(MessageCodeConstant.M005_INVALID, OTPConstant.OTP_NOT_FOUND) }

        if (otpVerification.expiryTime.isBefore(LocalDateTime.now())) {
            throw AppException(MessageCodeConstant.M005_INVALID, OTPConstant.OTP_EXPIRED_TIME)
        }

        if (otpVerification.otp != otp) {
            throw AppException(MessageCodeConstant.M005_INVALID, OTPConstant.OTP_NOT_VERIFY)
        }

        otpVerificationRepository.deleteByEmail(recipient)
        val user = userRepository.findByEmail(recipient).orElseThrow()
        user.status = UserStatus.ACTIVE
        userRepository.save(user)

        return true
    }

    private fun generateOTPEmail(): String {
        return "%06d".format(Random().nextInt(999_999))
    }
}
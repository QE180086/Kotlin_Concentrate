package app.concentrate.projectmanagement.usermanagement.service

import jakarta.mail.MessagingException

interface EmailService {
    @Throws(MessagingException::class)
    fun sendOTP(recipient: String)

    fun verifyOtp(recipient: String, otp: String): Boolean
}
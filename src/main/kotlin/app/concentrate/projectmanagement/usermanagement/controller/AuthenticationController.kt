package app.concentrate.projectmanagement.usermanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.projectmanagement.usermanagement.request.SendOTPRequest
import app.concentrate.projectmanagement.usermanagement.request.UserRequestLogin
import app.concentrate.projectmanagement.usermanagement.request.UserRequestRegister
import app.concentrate.projectmanagement.usermanagement.request.VerifyOtpRequest
import app.concentrate.projectmanagement.usermanagement.response.AuthResponseLogin
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import app.concentrate.projectmanagement.usermanagement.service.AuthenticationService
import app.concentrate.projectmanagement.usermanagement.service.EmailService
import jakarta.mail.MessagingException
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val emailService: EmailService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: UserRequestLogin): ResponseEntity<GenericResponse<AuthResponseLogin>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = authenticationService.login(request)
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    @Throws(MessagingException::class)
    fun register(@Valid @RequestBody request: UserRequestRegister): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = authenticationService.register(request)
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/sendOTP")
    @Throws(MessagingException::class)
    fun sendOTP(@Valid @RequestBody request: SendOTPRequest): ResponseEntity<GenericResponse<Void?>> {
        emailService.sendOTP(request.email)
        val response = GenericResponse<Void?>(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = null
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/verifyOTP")
    @Throws(MessagingException::class)
    fun verifyOTP(@Valid @RequestBody request: VerifyOtpRequest): ResponseEntity<GenericResponse<Void?>> {
        emailService.verifyOtp(request.email, request.otp)
        val response = GenericResponse<Void?>(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = null
        )
        return ResponseEntity.ok(response)
    }
}
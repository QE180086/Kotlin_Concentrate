package app.concentrate.projectmanagement.usermanagement.request

import jakarta.validation.constraints.NotBlank

data class VerifyOtpRequest(
    @field:NotBlank(message = "Email is not blank to send OTP.")
    val email: String,

    @field:NotBlank(message = "OTP is not null to verify account.")
    val otp: String
)
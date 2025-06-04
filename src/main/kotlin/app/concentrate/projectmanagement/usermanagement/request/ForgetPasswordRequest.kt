package app.concentrate.projectmanagement.usermanagement.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ForgetPasswordRequest(
    @field:Email(message = "Invalid email format.")
    @field:NotBlank(message = "Email cannot be blank.")
    val email: String,

    @field:NotBlank(message = "Username cannot be blank.")
    val username: String
)
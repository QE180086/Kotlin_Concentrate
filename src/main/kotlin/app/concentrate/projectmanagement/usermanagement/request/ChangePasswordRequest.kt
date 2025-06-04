package app.concentrate.projectmanagement.usermanagement.request


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
data class ChangePasswordRequest(
    val oldPassword: String,

    @field:NotBlank(message = "Password cannot be blank.")
    @field:Size(min = 6, message = "Password must be at least 6 characters long.")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one number."
    )
    val newPassword: String
)
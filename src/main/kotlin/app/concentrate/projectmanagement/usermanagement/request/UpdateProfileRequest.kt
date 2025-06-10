package app.concentrate.projectmanagement.usermanagement.request

import app.concentrate.projectmanagement.usermanagement.enumration.Gender
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.util.Date

data class UpdateProfileRequest (
    @field:Size(max = 20, message = "Nick name must have less than 20 char")
    var nickName: String? = null,

    @field:Size(max = 20, message = "Nick name must have less than 20 char")
    var fullName: String? = null,

    @field:Pattern(regexp = "\\d{10}", message = "Number phone must have ten number")
    var phoneNumber: String? = null,

    var dateOfBirth: Date? = null,
    var avatar: String? = null,
    var gender: Gender? = null,
)
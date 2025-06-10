package app.concentrate.projectmanagement.usermanagement.response

import app.concentrate.projectmanagement.usermanagement.enumration.Gender
import java.util.*

data class ProfileResponse(
    val nickName: String?,
    val fullName: String?,
    val phoneNumber: String?,
    val dateOfBirth: Date?,
    val avatar: String?,
    val gender: Gender?,

)
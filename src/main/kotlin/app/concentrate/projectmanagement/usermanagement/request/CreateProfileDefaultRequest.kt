package app.concentrate.projectmanagement.usermanagement.request

import app.concentrate.projectmanagement.usermanagement.entity.User
import app.concentrate.projectmanagement.usermanagement.enumration.Gender
import java.util.*

data class CreateProfileDefaultRequest(
    var nickName: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
    var dateOfBirth: Date? = null,
    var avatar: String? = null,
    var gender: Gender? = null,
    var user: User? = null,

    )
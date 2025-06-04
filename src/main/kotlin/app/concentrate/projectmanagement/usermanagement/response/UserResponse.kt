package app.concentrate.projectmanagement.usermanagement.response

import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus

data class UserResponse(
    val username: String,
    val password: String,
    val email: String,
    val status: UserStatus,
    val isDeleted: Boolean,
    val role: String
)
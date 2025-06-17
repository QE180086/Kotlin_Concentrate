package app.concentrate.projectmanagement.paymentmanagement.response

import app.concentrate.projectmanagement.usermanagement.entity.User

data class PointResponse(
    private val id: String,
    private val user: User,
    private val point: Int,
)
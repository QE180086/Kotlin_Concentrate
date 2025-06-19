package app.concentrate.projectmanagement.paymentmanagement.request

import app.concentrate.projectmanagement.usermanagement.entity.User

data class CreatePointRequest(
    private val user: User,
    private val point: Int = 0
)
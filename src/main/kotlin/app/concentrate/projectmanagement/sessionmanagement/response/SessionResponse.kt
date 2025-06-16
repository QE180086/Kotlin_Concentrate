package app.concentrate.projectmanagement.sessionmanagement.response

import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import app.concentrate.projectmanagement.usermanagement.entity.User
import java.util.*

data class SessionResponse(
    val id: String,
    val username: String,
    val startTime: Date?,
    val endTime: Date?,
    val status: StatusSession?,
    val duration: Long?,
    val subject: String?,
    val focusScore: Int,
    val penaltyPoints: Int,
)
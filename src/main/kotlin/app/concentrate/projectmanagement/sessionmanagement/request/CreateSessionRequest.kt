package app.concentrate.projectmanagement.sessionmanagement.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CreateSessionRequest (
    @field:NotBlank(message = "Subject must not be blank")
    val subject: String,

    @field:Min(value = 1, message = "Duration must be greater than 0")
    val durationMinutes: Long,

    val aiEnabled: Boolean = false,
)
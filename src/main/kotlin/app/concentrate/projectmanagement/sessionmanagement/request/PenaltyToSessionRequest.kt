package app.concentrate.projectmanagement.sessionmanagement.request

import jakarta.validation.constraints.NotBlank

data class PenaltyToSessionRequest(
    @field:NotBlank(message = "Session ID must not be blank")
    val sessionId: String,
    @field:NotBlank(message = "Penalty Rule ID must not be blank")
    val penaltyRuleId: String
)
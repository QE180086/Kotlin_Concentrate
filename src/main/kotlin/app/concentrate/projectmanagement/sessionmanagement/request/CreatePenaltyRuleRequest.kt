package app.concentrate.projectmanagement.sessionmanagement.request

import app.concentrate.projectmanagement.sessionmanagement.enumration.RuleType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreatePenaltyRuleRequest (
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Rule type must not be blank")
    val ruleType: RuleType,

    @field:NotBlank(message = "Description must not be blank")
    val description: String,

    @field:Min(value = 1, message = "Penalty points must be greater than 0")
    val penaltyPoints: Int,

    @field:NotNull(message = "isActive must not be null")
    val isActive: Boolean
)
package app.concentrate.projectmanagement.sessionmanagement.response

import app.concentrate.projectmanagement.sessionmanagement.enumration.RuleType

data class PenaltyRuleResponse(
    val id: String,
    val name: String?,
    val description: String?,
    val penaltyPoints: Int,
    val isActive: Boolean,
    val ruleType: RuleType
)

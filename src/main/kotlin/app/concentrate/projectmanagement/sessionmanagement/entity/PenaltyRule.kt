package app.concentrate.projectmanagement.sessionmanagement.entity

import app.concentrate.common.entity.BaseEntity
import app.concentrate.projectmanagement.sessionmanagement.enumration.RuleType
import jakarta.persistence.Entity

@Entity
data class PenaltyRule(
    var name: String? = null,
    var description: String? = null,
    var penaltyPoints: Int = 0,
    var isActive: Boolean = true,
    var ruleType: RuleType,
):BaseEntity() {
}
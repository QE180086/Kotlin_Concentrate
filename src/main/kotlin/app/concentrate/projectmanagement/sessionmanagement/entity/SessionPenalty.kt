package app.concentrate.projectmanagement.sessionmanagement.entity

import app.concentrate.common.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.util.*

@Entity
data class SessionPenalty(
    @ManyToOne(fetch = FetchType.LAZY)
    val session: Session,

    @ManyToOne(fetch = FetchType.LAZY)
    val penaltyRule: PenaltyRule,

    val appliedAt: Date = Date()
) : BaseEntity()
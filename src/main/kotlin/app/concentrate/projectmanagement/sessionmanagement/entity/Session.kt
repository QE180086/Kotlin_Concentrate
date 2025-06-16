package app.concentrate.projectmanagement.sessionmanagement.entity

import app.concentrate.common.entity.BaseEntity
import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import app.concentrate.projectmanagement.usermanagement.entity.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.util.Date

@Entity
data class Session(
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    var user: User? = null,
    var startTime: Date? = null,
    var endTime: Date? = null,
    var status: StatusSession? = null,
    var duration: Long? = null,
    var subject: String? = null,
    var isEnableAI: Boolean,
    var focusScore: Int = 0,
    var penaltyPoints: Int = 0,

    ) :BaseEntity()
package app.concentrate.projectmanagement.usermanagement.entity

import app.concentrate.common.entity.BaseEntity
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
data class OTPVerification(
    var email: String,
    var otp: String,
    var expiryTime: LocalDateTime
) : BaseEntity()
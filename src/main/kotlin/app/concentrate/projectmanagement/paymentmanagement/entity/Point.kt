package app.concentrate.projectmanagement.paymentmanagement.entity

import app.concentrate.common.entity.BaseEntity
import app.concentrate.projectmanagement.usermanagement.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
data class Point(
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,
    var point: Int = 0

):BaseEntity()
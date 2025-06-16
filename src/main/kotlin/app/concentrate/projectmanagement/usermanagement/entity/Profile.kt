package app.concentrate.projectmanagement.usermanagement.entity

import app.concentrate.common.entity.BaseEntity
import app.concentrate.projectmanagement.usermanagement.enumration.Gender
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.util.*

@Entity
data class Profile(
    var nickName: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
    var dateOfBirth: Date? = null,
    var avatar: String? = null,

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    var user: User? = null
) : BaseEntity()
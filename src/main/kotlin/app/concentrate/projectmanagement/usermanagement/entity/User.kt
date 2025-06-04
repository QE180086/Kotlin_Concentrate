package app.concentrate.projectmanagement.usermanagement.entity

import app.concentrate.common.entity.BaseEntity
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import lombok.ToString

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
data class User(
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,

    @Enumerated(EnumType.STRING)
    var status: UserStatus? = null,

    var isDeleted: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "role_name")
    var role: Role? = null,

//    @OneToOne(mappedBy = "user")
//    var profile: Profile? = null,

    ) : BaseEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (id == null || other.id == null) return false
        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}

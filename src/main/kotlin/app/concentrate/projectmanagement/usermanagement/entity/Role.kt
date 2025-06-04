package app.concentrate.projectmanagement.usermanagement.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Role(
    @Id
    var name: String? = null
)
package app.concentrate.projectmanagement.usermanagement.repository

import app.concentrate.projectmanagement.usermanagement.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, String> {
    fun findByName(roleName: String): Optional<Role>
}
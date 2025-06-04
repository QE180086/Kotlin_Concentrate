package app.concentrate.projectmanagement.usermanagement.repository

import app.concentrate.projectmanagement.usermanagement.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    @Query("SELECT u FROM User u WHERE (:searchText IS NULL OR u.email LIKE %:searchText%)")
    fun getAllUserBySearchText(
        @Param("searchText") searchText: String?,
        pageRequest: PageRequest
    ): Page<User>

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}

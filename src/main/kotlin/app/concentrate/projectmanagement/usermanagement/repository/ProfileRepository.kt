package app.concentrate.projectmanagement.usermanagement.repository

import app.concentrate.projectmanagement.usermanagement.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfileRepository :JpaRepository<Profile, String> {
    fun findByUserId(userId: String): Profile?

}
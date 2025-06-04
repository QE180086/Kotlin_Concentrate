package app.concentrate.projectmanagement.usermanagement.repository

import app.concentrate.projectmanagement.usermanagement.entity.OTPVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OTPVerificationRepository : JpaRepository<OTPVerification, String> {
    fun findByEmail(email: String): Optional<OTPVerification>
    fun deleteByEmail(email: String)
}
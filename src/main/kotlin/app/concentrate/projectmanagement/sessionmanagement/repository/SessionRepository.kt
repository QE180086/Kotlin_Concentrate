package app.concentrate.projectmanagement.sessionmanagement.repository

import app.concentrate.projectmanagement.sessionmanagement.entity.Session
import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface SessionRepository : JpaRepository<Session, String> {
    @Query(
        """
    SELECT s FROM Session s
    WHERE s.user.id = :userId
    AND (:searchText IS NULL OR LOWER(s.subject) LIKE LOWER(CONCAT('%', :searchText, '%')))
"""
    )
    fun getAllSessionByCurrentUser(
        @Param("searchText") searchText: String?,
        @Param("userId") userId: String,
        pageRequest: PageRequest
    ): Page<Session>

    @Query(
        """
    SELECT s FROM Session s 
    WHERE (:searchText IS NULL OR LOWER(s.subject) LIKE LOWER(CONCAT('%', :searchText, '%')))
"""
    )
    fun getAllSession(
        @Param("searchText") searchText: String?,
        pageRequest: PageRequest
    ): Page<Session>

    fun existsByUserIdAndStatus(
        userId: String,
        status: StatusSession
    ): Boolean

    fun findByStatusAndEndTimeBefore(
        status: StatusSession,
        endTime: Date
    ): List<Session>
}
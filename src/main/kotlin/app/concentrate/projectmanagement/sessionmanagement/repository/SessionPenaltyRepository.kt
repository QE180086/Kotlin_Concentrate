package app.concentrate.projectmanagement.sessionmanagement.repository

import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import app.concentrate.projectmanagement.sessionmanagement.entity.Session
import app.concentrate.projectmanagement.sessionmanagement.entity.SessionPenalty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionPenaltyRepository : JpaRepository<SessionPenalty, String>{
    fun deleteAllBySession(session: Session)
    fun existsByPenaltyRule(penaltyRule: PenaltyRule): Boolean
}
package app.concentrate.projectmanagement.sessionmanagement.repository

import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PenaltyRuleRepository : JpaRepository<PenaltyRule, String> {
    @Query(
        """
    SELECT pr FROM PenaltyRule pr
    WHERE (:searchText IS NULL OR LOWER(pr.name) LIKE LOWER(CONCAT('%', :searchText, '%')))
"""
    )
    fun getAllPenaltyRule(@Param("searchText") searchText: String?, request: PageRequest): Page<PenaltyRule>

    @Query(
        """
    SELECT sp.penaltyRule FROM SessionPenalty sp
    WHERE sp.session.id = :sessionId
"""
    )
    fun findPenaltyRulesBySessionId(@Param("sessionId") sessionId: String): List<PenaltyRule>

}
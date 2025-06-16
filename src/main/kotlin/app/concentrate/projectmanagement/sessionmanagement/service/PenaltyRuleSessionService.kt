package app.concentrate.projectmanagement.sessionmanagement.service

import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.sessionmanagement.request.PenaltyToSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.request.CreatePenaltyRuleRequest
import app.concentrate.projectmanagement.sessionmanagement.response.PenaltyRuleResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse

interface PenaltyRuleSessionService {
    fun create(request : CreatePenaltyRuleRequest): PenaltyRuleResponse
    fun delete(penaltyRuleId: String)
    fun getDetailPenaltyRule(penaltyRuleId: String): PenaltyRuleResponse
    fun getListPenaltyRule(searchText: String, request: PagingRequest): PagingResponse<PenaltyRuleResponse>
    fun applyPenaltyToSession(request: PenaltyToSessionRequest): SessionPenaltyResponse
    fun removePenaltyFromSession(request: PenaltyToSessionRequest): SessionResponse
    fun getPenaltiesBySession(sessionId: String): List<PenaltyRuleResponse>
}
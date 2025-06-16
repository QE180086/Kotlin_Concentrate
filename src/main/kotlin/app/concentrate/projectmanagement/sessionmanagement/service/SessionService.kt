package app.concentrate.projectmanagement.sessionmanagement.service

import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.sessionmanagement.request.CreateSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse

interface SessionService {
    fun createSession(request: CreateSessionRequest): SessionResponse
    fun getSessionById(id: String): SessionPenaltyResponse
    fun getAllSessions(searchText: String,request: PagingRequest): PagingResponse<SessionResponse>
//    fun updateSession(id: String, request: SessionRequest): SessionResponse
    fun deleteSession(id: String)
    fun getSessionsByUser(searchText: String,request: PagingRequest): PagingResponse<SessionResponse>
}
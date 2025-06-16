package app.concentrate.projectmanagement.sessionmanagement.service.impl

import app.concentrate.common.constant.GlobalVariable
import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.exception.AppException
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.common.utils.PagingUtil
import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import app.concentrate.projectmanagement.sessionmanagement.entity.Session
import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import app.concentrate.projectmanagement.sessionmanagement.mapper.SessionMapper
import app.concentrate.projectmanagement.sessionmanagement.repository.PenaltyRuleRepository
import app.concentrate.projectmanagement.sessionmanagement.repository.SessionPenaltyRepository
import app.concentrate.projectmanagement.sessionmanagement.repository.SessionRepository
import app.concentrate.projectmanagement.sessionmanagement.request.CreateSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse
import app.concentrate.projectmanagement.sessionmanagement.service.SessionService
import app.concentrate.projectmanagement.usermanagement.service.UserUtilService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.List

@Service
@RequiredArgsConstructor
class SessionServiceImpl(
    private val sessionRepository: SessionRepository,
    private val sessionMapper: SessionMapper,
    private val utilService: UserUtilService,
    private val sessionPenaltyRepository: SessionPenaltyRepository,
    private val penaltyRepository: PenaltyRuleRepository,

    ) : SessionService {
    override fun createSession(request: CreateSessionRequest): SessionResponse {
        if (utilService.getCurrentUser() == null) {
            throw AppException(MessageCodeConstant.M006_UNAUTHORIZED, MessageConstant.DATA_NOT_FOUND)
        }
        val existingSession =
            utilService.getCurrentUser()!!.id?.let {
                sessionRepository.existsByUserIdAndStatus(
                    it,
                    StatusSession.ACTIVE
                )
            }
        if (existingSession == true) {
            throw AppException(MessageCodeConstant.M005_INVALID, "User already has an active session")
        }
        val session = Session(
            user = utilService.getCurrentUser(),
            subject = request.subject,
            status = StatusSession.ACTIVE,
            duration = request.durationMinutes,
            startTime = Date(),
            endTime = Date(Date().time + request.durationMinutes * 60 * 1000),
            isEnableAI = request.aiEnabled,
        )
        val saved = sessionRepository.save(session)
        return sessionMapper.toDTO(saved)
    }

    override fun getSessionById(id: String): SessionPenaltyResponse {
        val penalties: List<PenaltyRule> = penaltyRepository.findPenaltyRulesBySessionId(id)
        return sessionMapper.toDTO(sessionRepository.findById(id).orElseThrow {
            AppException(MessageCodeConstant.M003_NOT_FOUND, MessageConstant.DATA_NOT_FOUND)
        }, penalties)
    }

    override fun getAllSessions(searchText: String, request: PagingRequest): PagingResponse<SessionResponse> {
        val sort: Sort = PagingUtil.createSort(request)
        val pageRequest = PageRequest.of(
            request.page - GlobalVariable.PAGE_SIZE_INDEX,
            request.size,
            sort
        )
        val sessionPage =
            sessionRepository.getAllSession(searchText, pageRequest)
        val sessionResponse = sessionMapper.toListDTO(sessionPage.content)
        return PagingResponse(sessionResponse, request, sessionPage.totalElements)
    }

    override fun deleteSession(id: String) {
        val session = sessionRepository.findById(id).orElseThrow {
            AppException(MessageCodeConstant.M003_NOT_FOUND, MessageConstant.DATA_NOT_FOUND)
        }
        if (session.status != StatusSession.ENDED) {
            throw AppException(MessageCodeConstant.M005_INVALID, "Cannot delete session that is not ended")
        }
        sessionPenaltyRepository.deleteAllBySession(session)
        sessionRepository.delete(session)
    }

    override fun getSessionsByUser(searchText: String, request: PagingRequest): PagingResponse<SessionResponse> {
        val user = utilService.getCurrentUser()
            ?: throw AppException(MessageCodeConstant.M006_UNAUTHORIZED, MessageConstant.DATA_NOT_FOUND)
        val sort: Sort = PagingUtil.createSort(request)
        val pageRequest = PageRequest.of(
            request.page - GlobalVariable.PAGE_SIZE_INDEX,
            request.size,
            sort
        )
        val sessionPage =
            sessionRepository.getAllSessionByCurrentUser(searchText, user.id.toString(), pageRequest)
        val sessionResponse = sessionMapper.toListDTO(sessionPage.content)
        return PagingResponse(sessionResponse, request, sessionPage.totalElements)
    }
}
package app.concentrate.projectmanagement.sessionmanagement.service.impl

import app.concentrate.common.constant.GlobalVariable
import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.AppException
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.common.utils.PagingUtil
import app.concentrate.projectmanagement.sessionmanagement.entity.PenaltyRule
import app.concentrate.projectmanagement.sessionmanagement.entity.SessionPenalty
import app.concentrate.projectmanagement.sessionmanagement.enumration.RuleType
import app.concentrate.projectmanagement.sessionmanagement.enumration.StatusSession
import app.concentrate.projectmanagement.sessionmanagement.mapper.PenaltyRuleMapper
import app.concentrate.projectmanagement.sessionmanagement.mapper.SessionMapper
import app.concentrate.projectmanagement.sessionmanagement.repository.PenaltyRuleRepository
import app.concentrate.projectmanagement.sessionmanagement.repository.SessionPenaltyRepository
import app.concentrate.projectmanagement.sessionmanagement.repository.SessionRepository
import app.concentrate.projectmanagement.sessionmanagement.request.CreatePenaltyRuleRequest
import app.concentrate.projectmanagement.sessionmanagement.request.PenaltyToSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.response.PenaltyRuleResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse
import app.concentrate.projectmanagement.sessionmanagement.service.PenaltyRuleSessionService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class PenaltyRuleSessionImpl(
    private val penaltyRepository: PenaltyRuleRepository,
    private val sessionPenaltyRepository: SessionPenaltyRepository,
    private val penaltyRuleMapper: PenaltyRuleMapper,
    private val sessionRepository: SessionRepository,
    private val sessionMapper: SessionMapper,
) : PenaltyRuleSessionService {
    override fun create(request: CreatePenaltyRuleRequest): PenaltyRuleResponse {
        val allowedTypes = setOf(RuleType.EXIT_APP, RuleType.NO_FOCUS, RuleType.PHONE_USAGE)
        if (request.ruleType !in allowedTypes) {
            throw AppException(
                MessageCodeConstant.M016_VALIDATION_FAILED,
                "Invalid rule type: ${request.ruleType}. Allowed types are: $allowedTypes"
            )
        }
        val penaltyRule = PenaltyRule(
            name = request.name,
            description = request.description,
            ruleType = request.ruleType,
            penaltyPoints = request.penaltyPoints,
            isActive = request.isActive
        )
        val savedPenaltyRule = penaltyRepository.save(penaltyRule)
        return penaltyRuleMapper.toDTO(savedPenaltyRule)
    }

    override fun delete(penaltyRuleId: String) {
        if (penaltyRuleId.isBlank()) {
            throw AppException(MessageCodeConstant.M016_VALIDATION_FAILED, "Penalty rule ID cannot be blank")
        }
        val penaltyRule = penaltyRepository.findById(penaltyRuleId).orElseThrow {
            AppException(MessageCodeConstant.M003_NOT_FOUND, "Penalty rule not found")
        }
        if (sessionPenaltyRepository.existsByPenaltyRule(penaltyRule)) {
            throw AppException(MessageCodeConstant.M004_DUPLICATE, "Penalty rule is in use and cannot be deleted")
        }
        penaltyRepository.delete(penaltyRule)
    }

    override fun getDetailPenaltyRule(penaltyRuleId: String): PenaltyRuleResponse {
        if (penaltyRuleId.isBlank()) {
            throw AppException(MessageCodeConstant.M016_VALIDATION_FAILED, "Penalty rule ID cannot be blank")
        }
        val penaltyRule = penaltyRepository.findById(penaltyRuleId).orElseThrow {
            AppException(MessageCodeConstant.M003_NOT_FOUND, "Penalty rule not found")
        }
        return penaltyRuleMapper.toDTO(penaltyRule)
    }

    override fun getListPenaltyRule(searchText: String, request: PagingRequest): PagingResponse<PenaltyRuleResponse> {
        val sort: Sort = PagingUtil.createSort(request)
        val pageRequest = PageRequest.of(
            request.page - GlobalVariable.PAGE_SIZE_INDEX,
            request.size,
            sort
        )
        val penaltyRulePage =
            penaltyRepository.getAllPenaltyRule(searchText, pageRequest)
        val penaltyRuleResponse = penaltyRuleMapper.toListDTO(penaltyRulePage.content)
        return PagingResponse(penaltyRuleResponse, request, penaltyRulePage.totalElements)
    }

    override fun applyPenaltyToSession(request: PenaltyToSessionRequest): SessionPenaltyResponse {
        val session = sessionRepository.findById(request.sessionId).orElseThrow {
            throw AppException(MessageCodeConstant.M003_NOT_FOUND, "Session not found")
        }
        val penaltyRule = penaltyRepository.findById(request.penaltyRuleId).orElseThrow {
            throw AppException(MessageCodeConstant.M003_NOT_FOUND, "Penalty rule not found")
        }
        if (!penaltyRule.isActive) {
            throw AppException(MessageCodeConstant.M016_VALIDATION_FAILED, "Penalty rule is not active")
        }
        if (session.status != StatusSession.ACTIVE) {
            throw AppException(MessageCodeConstant.M005_INVALID, "Cannot apply penalty to a non-active session")
        }
        session.penaltyPoints += penaltyRule.penaltyPoints
        sessionRepository.save(session)
        // check ponit hien tai cua user
        // minus ponit
        // neu diem trong session penalty < 10 diem thi session ép buọc dung
        val sessionPenalty = SessionPenalty(
            session = session,
            penaltyRule = penaltyRule,
            appliedAt = Date()
        )
        sessionPenaltyRepository.save(sessionPenalty)

        val penalties = penaltyRepository.findPenaltyRulesBySessionId(request.sessionId)
        return sessionMapper.toDTO(session, penalties)
    }

    override fun removePenaltyFromSession(request: PenaltyToSessionRequest): SessionResponse {
        TODO("Not yet implemented")
    }

    override fun getPenaltiesBySession(sessionId: String): List<PenaltyRuleResponse> {
       return penaltyRepository.findPenaltyRulesBySessionId(sessionId).map { penaltyRule ->
            penaltyRuleMapper.toDTO(penaltyRule)
        }
    }
}
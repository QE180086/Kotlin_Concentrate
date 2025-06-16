package app.concentrate.projectmanagement.sessionmanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.request.SortRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.sessionmanagement.request.CreatePenaltyRuleRequest
import app.concentrate.projectmanagement.sessionmanagement.request.PenaltyToSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.response.PenaltyRuleResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.service.PenaltyRuleSessionService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/penalty-rules")
class PenaltyRuleController(
    private val penaltyRule: PenaltyRuleSessionService,
) {
    @GetMapping
    fun getAllPenaltyRule(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "createdDate") field: String,
        @RequestParam(required = false, defaultValue = "desc") direction: String,
        @RequestParam(required = false, defaultValue = "") searchText: String
    ): ResponseEntity<GenericResponse<PagingResponse<PenaltyRuleResponse>>> {

        val pagingRequest = PagingRequest(page, size, SortRequest(direction, field))
        val penalties =  penaltyRule.getListPenaltyRule(searchText, pagingRequest)

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = penalties
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createPenaltyRule(@Valid @RequestBody request: CreatePenaltyRuleRequest): ResponseEntity<GenericResponse<PenaltyRuleResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.CREATE_DATA_SUCCESS
            ),
            isSuccess = true,
            data = penaltyRule.create(request)
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deletePenaltyRule(@PathVariable id: String): ResponseEntity<GenericResponse<Void>> {
        penaltyRule.delete(id)
        val response = GenericResponse<Void>(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.DELETE_DATA_SUCCESS
            ),
            isSuccess = true,
            data = null
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getDetailPenaltyRule(@PathVariable id:String): ResponseEntity<GenericResponse<PenaltyRuleResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = penaltyRule.getDetailPenaltyRule(id)
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/apply-penalty")
    fun applyPenaltyToSession(@Valid @RequestBody request: PenaltyToSessionRequest): ResponseEntity<GenericResponse<SessionPenaltyResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.CREATE_DATA_SUCCESS
            ),
            isSuccess = true,
            data = penaltyRule.applyPenaltyToSession(request)
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{sessionId}/penalties")
    fun getListPenaltyFromSession(@PathVariable sessionId:String): ResponseEntity<GenericResponse<List<PenaltyRuleResponse>>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = penaltyRule.getPenaltiesBySession(sessionId)
        )
        return ResponseEntity.ok(response)
    }

}
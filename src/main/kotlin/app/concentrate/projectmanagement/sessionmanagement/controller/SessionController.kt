package app.concentrate.projectmanagement.sessionmanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.request.SortRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.sessionmanagement.request.CreateSessionRequest
import app.concentrate.projectmanagement.sessionmanagement.response.SessionPenaltyResponse
import app.concentrate.projectmanagement.sessionmanagement.response.SessionResponse
import app.concentrate.projectmanagement.sessionmanagement.service.SessionService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/session")
class SessionController(
    private val sessionService: SessionService
) {
    @GetMapping
    fun getAllSession(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "createdDate") field: String,
        @RequestParam(required = false, defaultValue = "desc") direction: String,
        @RequestParam(required = false, defaultValue = "") searchText: String
    ): ResponseEntity<GenericResponse<PagingResponse<SessionResponse>>> {

        val pagingRequest = PagingRequest(page, size, SortRequest(direction, field))
        val sessions =  sessionService.getAllSessions(searchText, pagingRequest)

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = sessions
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createSession(@Valid @RequestBody request: CreateSessionRequest): ResponseEntity<GenericResponse<SessionResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.CREATE_DATA_SUCCESS
            ),
            isSuccess = true,
            data = sessionService.createSession(request)
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getSessionDetailById(@PathVariable id: String): ResponseEntity<GenericResponse<SessionPenaltyResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = sessionService.getSessionById(id)
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteSession(@PathVariable id: String): ResponseEntity<GenericResponse<Void>> {
        sessionService.deleteSession(id)
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

    @GetMapping("/user")
    fun getAllSessionByCurrentUser(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "createdDate") field: String,
        @RequestParam(required = false, defaultValue = "desc") direction: String,
        @RequestParam(required = false, defaultValue = "") searchText: String
    ): ResponseEntity<GenericResponse<PagingResponse<SessionResponse>>> {

        val pagingRequest = PagingRequest(page, size, SortRequest(direction, field))
        val sessions =  sessionService.getSessionsByUser(searchText, pagingRequest)

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.GET_DATA_SUCCESS
            ),
            isSuccess = true,
            data = sessions
        )

        return ResponseEntity.ok(response)
    }

}
package app.concentrate.projectmanagement.usermanagement.controller

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.constant.MessageConstant
import app.concentrate.common.dto.GenericResponse
import app.concentrate.common.dto.MessageDTO
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.request.SortRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.usermanagement.constant.UserConstant
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.request.ChangePasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ForgetPasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ResetPassword
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import app.concentrate.projectmanagement.usermanagement.service.UserService
import app.concentrate.projectmanagement.usermanagement.service.impl.UserServiceImpl
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@Slf4j
class UserController(
    private val userService: UserService
) {
    private val log: org.slf4j.Logger? = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @GetMapping
    fun getAllUserBySearchText(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "createdDate") field: String,
        @RequestParam(required = false, defaultValue = "desc") direction: String,
        @RequestParam(required = false, defaultValue = "") searchText: String
    ): ResponseEntity<GenericResponse<PagingResponse<UserResponse>>> {

        val pagingRequest = PagingRequest(page, size, SortRequest(direction, field))
        val users =  userService.getAllUser(searchText, pagingRequest)

        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = users
        )

        return ResponseEntity.ok(response)
    }

    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody request: ChangePasswordRequest): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = userService.changePassword(request)
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/forget-password")
    fun forgetPassword(@Valid @RequestBody request: ForgetPasswordRequest): ResponseEntity<GenericResponse<Void>> {
        userService.forgetPassword(request)
        val response = GenericResponse<Void>(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@Valid @RequestBody request: ResetPassword): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = userService.resetPassword(request)
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/get-detail")
    fun getDetailUser(): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = MessageConstant.SUCCESS
            ),
            isSuccess = true,
            data = userService.getDetailUser()
        )
        return ResponseEntity.ok(response)
    }
//
//    @PutMapping("/assign-role/{userId}")
//    fun assignRoleForUser(
//        @PathVariable userId: String,
//        @Valid @RequestBody role: UpdateRoleRequest
//    ): ResponseEntity<GenericResponse<UserResponse>> {
//        val response = GenericResponse(
//            message = MessageDTO(
//                messageCode = MessageCodeConstant.M001_SUCCESS,
//                messageDetail = UserConstant.UPDATE_USER_SUCCESS
//            ),
//            isSuccess = true,
//            data = userService.assignRole(userId, role)
//        )
//        return ResponseEntity.ok(response)
//    }

    @PutMapping("/status/{userId}")
    fun setStatus(
        @PathVariable userId: String,
        @RequestParam status: UserStatus
    ): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = UserConstant.UPDATE_USER_SUCCESS
            ),
            isSuccess = true,
            data = userService.setStatus(userId, status)
        )
        return ResponseEntity.ok(response)
    }

    @PutMapping("/delete/{userId}")
    fun setDelete(
        @PathVariable userId: String,
        @RequestParam(defaultValue = "true") isDeleted: Boolean
    ): ResponseEntity<GenericResponse<UserResponse>> {
        val response = GenericResponse(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = UserConstant.UPDATE_USER_SUCCESS
            ),
            isSuccess = true,
            data = userService.setDelete(userId, isDeleted)
        )
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: String): ResponseEntity<GenericResponse<Void>> {
        userService.deleteUser(userId)
        val response = GenericResponse<Void>(
            message = MessageDTO(
                messageCode = MessageCodeConstant.M001_SUCCESS,
                messageDetail = UserConstant.DELETE_USER_SUCCESS
            ),
            isSuccess = true
        )
        return ResponseEntity.ok(response)
    }
}
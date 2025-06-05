package app.concentrate.projectmanagement.usermanagement.service

import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.request.ChangePasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ForgetPasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ResetPassword
import app.concentrate.projectmanagement.usermanagement.response.UserResponse

interface UserService {
    fun getAllUser(searchText: String, request: PagingRequest): PagingResponse<UserResponse>

    fun changePassword(request: ChangePasswordRequest): UserResponse

    fun forgetPassword(request: ForgetPasswordRequest)

    fun resetPassword(resetPassword: ResetPassword): UserResponse

    fun getDetailUser(): UserResponse

//    fun assignRole(userId: String, role: UpdateRoleRequest): UserResponse

    fun setStatus(userId: String, status: UserStatus): UserResponse

    fun setDelete(userId: String, isDelete: Boolean): UserResponse

    fun deleteUser(userId: String)
}
package app.concentrate.projectmanagement.usermanagement.service.impl

import app.concentrate.common.constant.GlobalVariable
import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.AppException
import app.concentrate.common.request.PagingRequest
import app.concentrate.common.response.PagingResponse
import app.concentrate.common.utils.PagingUtil
import app.concentrate.projectmanagement.usermanagement.constant.OTPConstant
import app.concentrate.projectmanagement.usermanagement.constant.UserConstant
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.mapper.UserMapper
import app.concentrate.projectmanagement.usermanagement.repository.RoleRepository
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import app.concentrate.projectmanagement.usermanagement.request.ChangePasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ForgetPasswordRequest
import app.concentrate.projectmanagement.usermanagement.request.ResetPassword
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import app.concentrate.projectmanagement.usermanagement.service.EmailService
import app.concentrate.projectmanagement.usermanagement.service.UserService
import app.concentrate.projectmanagement.usermanagement.service.UserUtilService
import jakarta.mail.MessagingException
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val userUtilService: UserUtilService,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val roleRepository: RoleRepository,
) : UserService {

    override fun getAllUser(searchText: String, pagingRequest: PagingRequest): PagingResponse<UserResponse> {
        val sort: Sort = PagingUtil.createSort(pagingRequest)
        val pageRequest = PageRequest.of(
            pagingRequest.page - GlobalVariable.PAGE_SIZE_INDEX,
            pagingRequest.size,
            sort
        )
        val userPage = userRepository.getAllUserBySearchText(searchText, pageRequest)
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)

        val userResponses = userMapper.toListUserDTO(userPage.content)
        return PagingResponse(userResponses, pagingRequest, userPage.totalElements)
    }

    override fun changePassword(request: ChangePasswordRequest): UserResponse {
        val user = userUtilService.getCurrentUser()
            ?: throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)
        if (!passwordEncoder.matches(request.oldPassword, user.password)) {
            throw AppException(MessageCodeConstant.M005_INVALID, "New password is not matches with current password.")
        }
        user.password = passwordEncoder.encode(request.newPassword)
        userRepository.save(user)
        return userMapper.toUserDTO(user)
    }

    override fun forgetPassword(request: ForgetPasswordRequest) {
        if (!userRepository.existsByEmail(request.email)) {
            throw AppException(MessageCodeConstant.M005_INVALID, "Email is not registered.")
        }
        if (!userRepository.existsByUsername(request.username)) {
            throw AppException(MessageCodeConstant.M005_INVALID, "Username is not found.")
        }
        try {
            emailService.sendOTP(request.email)
        } catch (e: MessagingException) {
            throw RuntimeException(e)
        }
    }

    override fun resetPassword(resetPassword: ResetPassword): UserResponse {
        val user = userRepository.findByEmail(resetPassword.email)
            .orElseThrow { AppException(MessageCodeConstant.M005_INVALID, "Email is not registered.") }

        if (!emailService.verifyOtp(resetPassword.email, resetPassword.otp)) {
            throw AppException(MessageCodeConstant.M005_INVALID, OTPConstant.OTP_VALID_FAIL)
        }
        user.password = passwordEncoder.encode(resetPassword.newPassword)
        userRepository.save(user)
        return userMapper.toUserDTO(user)
    }

    override fun getDetailUser(userId: String): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND) }
        return userMapper.toUserDTO(user)
    }

//    override fun assignRole(userId: String, request: UpdateRoleRequest): UserResponse {
//        val user = userRepository.findById(userId)
//            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND) }
//        val role = roleRepository.findByName(request.roleName)
//            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, "Role is not found with role name ${request.roleName}") }
//
//        if (user.role.name != request.roleName) {
//            user.role = role
//            userRepository.save(user)
//            return userMapper.toUserDTO(user)
//        }
//        throw AppException(MessageCodeConstant.M026_FAIL, "Role is not saved with name  ${request.roleName}")
//    }

    override fun setStatus(userId: String, status: UserStatus): UserResponse {
        val currentUser = userUtilService.getCurrentUser()
        if( currentUser == null) {
            throw AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND)
        }
        if (!currentUser.role?.name.equals("ADMIN", ignoreCase = true)) {
            throw AppException(MessageCodeConstant.M006_UNAUTHORIZED, "Only ADMIN can change user status.")
        }
        val user = userRepository.findById(userId)
            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND) }

        if (user.status != status) {
            user.status = status
            userRepository.save(user)
        }
        return userMapper.toUserDTO(user)
    }

    override fun setDelete(userId: String, isDelete: Boolean): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND) }
        if (user.isDeleted != isDelete) {
            user.isDeleted = isDelete
            userRepository.save(user)
            return userMapper.toUserDTO(user)
        }
        throw AppException(MessageCodeConstant.M002_ERROR, UserConstant.DELETE_USER_FAIL)
    }

    @Transactional
    override fun deleteUser(userId: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { AppException(MessageCodeConstant.M003_NOT_FOUND, UserConstant.USER_NOT_FOUND) }
        userRepository.delete(user)
    }
}
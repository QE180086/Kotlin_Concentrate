package app.concentrate.projectmanagement.usermanagement.service.impl

import app.concentrate.common.constant.MessageCodeConstant
import app.concentrate.common.exception.AppException
import app.concentrate.projectmanagement.configuration.jwt.JwtUtil
import app.concentrate.projectmanagement.usermanagement.constant.OTPConstant
import app.concentrate.projectmanagement.usermanagement.constant.StartDefinedRole
import app.concentrate.projectmanagement.usermanagement.constant.UserConstant
import app.concentrate.projectmanagement.usermanagement.entity.User
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.mapper.UserMapper
import app.concentrate.projectmanagement.usermanagement.repository.RoleRepository
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import app.concentrate.projectmanagement.usermanagement.request.UserRequestLogin
import app.concentrate.projectmanagement.usermanagement.request.UserRequestRegister
import app.concentrate.projectmanagement.usermanagement.response.AuthResponseLogin
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import app.concentrate.projectmanagement.usermanagement.service.AuthenticationService
import app.concentrate.projectmanagement.usermanagement.service.EmailService
import jakarta.mail.MessagingException
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val emailService: EmailService,
    private val userMapper: UserMapper,
//    private val profileRepository: ProfileRepository,
//    private val profileService: ProfileService
) : AuthenticationService {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder)
        }
        return ProviderManager(listOf(authProvider))
    }

    override fun login(request: UserRequestLogin): AuthResponseLogin {
        val authentication: Authentication = authenticationManager().authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val userDetails = authentication.principal as UserDetails
        return AuthResponseLogin (
            accessToken = jwtUtil.generateToken(userDetails)
        )
    }

    @Throws(MessagingException::class)
    override fun register(register: UserRequestRegister): UserResponse {
        userRepository.findByEmail(register.email)?.let {
            throw AppException(MessageCodeConstant.M005_INVALID, UserConstant.GMAIL_IS_EXIST)
        }
        userRepository.findByUsername(register.username)?.let {
            throw AppException(MessageCodeConstant.M005_INVALID, UserConstant.USERNAME_IS_EXIST)
        }

        val user = User().apply {
            status = UserStatus.INACTIVE
            role = roleRepository.findByName(StartDefinedRole.USER).orElseThrow {
                AppException(MessageCodeConstant.M005_INVALID, UserConstant.ROLE_NOT_FOUND)
            }
            isDeleted = false
            username = register.username
            email = register.email
            password = passwordEncoder.encode(register.password)
        }

        try {
            emailService.sendOTP(register.email)
        } catch (e: MessagingException) {
            throw AppException(MessageCodeConstant.M005_INVALID, OTPConstant.SEND_OTP_FAIL)
        }

        userRepository.save(user)

//        val request = CreateProfileDefaultRequest {
//            this.user = user
//            gender = Gender.MALE
//            fullName = user.username
//        }

//        profileService.createDefaultProfile(request)
        return userMapper.toUserDTO(user)
    }
}
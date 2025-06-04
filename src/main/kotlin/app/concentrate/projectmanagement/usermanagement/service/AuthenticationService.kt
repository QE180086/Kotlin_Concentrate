package app.concentrate.projectmanagement.usermanagement.service

import app.concentrate.projectmanagement.usermanagement.request.UserRequestLogin
import app.concentrate.projectmanagement.usermanagement.request.UserRequestRegister
import app.concentrate.projectmanagement.usermanagement.response.AuthResponseLogin
import app.concentrate.projectmanagement.usermanagement.response.UserResponse
import jakarta.mail.MessagingException

interface AuthenticationService {
    fun login(request: UserRequestLogin): AuthResponseLogin

    @Throws(MessagingException::class)
    fun register(register: UserRequestRegister): UserResponse
}
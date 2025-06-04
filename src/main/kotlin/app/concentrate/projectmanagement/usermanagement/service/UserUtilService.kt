package app.concentrate.projectmanagement.usermanagement.service

import app.concentrate.projectmanagement.usermanagement.entity.User
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.Authentication


@Service
class UserUtilService(private val userRepository: UserRepository) {

    fun getCurrentUsername(): String? {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.principal is UserDetails) {
            return (authentication.principal as UserDetails).username
        }
        return null
    }

    fun getCurrentUser(): User? {
        val username = getCurrentUsername()
        return if (username != null) {
            userRepository.findByUsername(username).orElse(null)
        } else {
            null
        }
    }

    fun getIdCurrentUser(): String? {
        val username = getCurrentUsername()
        return if (username != null) {
            userRepository.findByUsername(username).map { it.id }.orElse(null)
        } else {
            null
        }
    }
}
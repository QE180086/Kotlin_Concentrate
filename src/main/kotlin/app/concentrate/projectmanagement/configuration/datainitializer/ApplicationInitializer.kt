package app.concentrate.projectmanagement.configuration.datainitializer

import app.concentrate.projectmanagement.usermanagement.constant.StartDefinedRole
import app.concentrate.projectmanagement.usermanagement.entity.Role
import app.concentrate.projectmanagement.usermanagement.entity.User
import app.concentrate.projectmanagement.usermanagement.enumration.UserStatus
import app.concentrate.projectmanagement.usermanagement.repository.RoleRepository
import app.concentrate.projectmanagement.usermanagement.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationInitializer(
    @Value("\${admin.username}") private val adminUsername: String,
    @Value("\${admin.password}") private val adminPassword: String
) {

    private val log = LoggerFactory.getLogger(ApplicationInitializer::class.java)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @ConditionalOnProperty(
        prefix = "spring",
        value = ["datasource.driverClassName"],
        havingValue = "com.mysql.cj.jdbc.Driver"
    )
    fun applicationRunner(
        userRepository: UserRepository,
        roleRepository: RoleRepository
    ): ApplicationRunner {
        log.info("Initializing application.....")

        return ApplicationRunner {
            if (userRepository.findByUsername(adminUsername).isEmpty) {
                // Save role only once
                val adminRole = roleRepository.save(
                    Role(name = StartDefinedRole.ADMIN)
                )

                val user = User(
                    username = adminUsername,
                    password = passwordEncoder().encode(adminPassword),
                    status = UserStatus.ACTIVE,
                    role = adminRole
                )

                userRepository.save(user)
                log.warn(
                    "Admin user has been created with username {} and password: {}, please change it",
                    adminUsername,
                    adminPassword
                )
            }
            log.info("Application initialization completed .....")
        }
    }
}
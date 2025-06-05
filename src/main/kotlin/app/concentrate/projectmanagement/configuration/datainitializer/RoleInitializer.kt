package app.concentrate.projectmanagement.configuration.datainitializer

import app.concentrate.projectmanagement.usermanagement.constant.StartDefinedRole
import app.concentrate.projectmanagement.usermanagement.entity.Role
import app.concentrate.projectmanagement.usermanagement.repository.RoleRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleInitializer(
    private val roleRepository: RoleRepository
) {

    private val log = LoggerFactory.getLogger(RoleInitializer::class.java)

    @Bean
    fun initializeRoles(): ApplicationRunner
    {
        return ApplicationRunner {
            log.info("Initializing roles.....")
            initializeRole(StartDefinedRole.ADMIN)
            initializeRole(StartDefinedRole.USER)
            log.info("Role initialization completed .....")
        }
    }

    private fun initializeRole(roleName: String) {
        if (roleRepository.findByName(roleName).isEmpty) {
            roleRepository.save(Role(name = roleName))
            log.info("Role {} has been created", roleName)
        }
    }
}
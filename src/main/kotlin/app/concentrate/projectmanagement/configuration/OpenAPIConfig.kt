package app.concentrate.projectmanagement.configuration
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_AUTH = "bearerAuth"
        private const val BEARER = "bearer"
        private const val JWT = "JWT"
        private const val DEV_SERVER_URLS = "http://localhost:8080;https://your-render-app.onrender.com"
    }

    @Bean
    fun customizeOpenAPI(): OpenAPI {
        val securitySchemeName = BEARER_AUTH
        val urls = DEV_SERVER_URLS.split(";")
        val servers = urls.map { Server().url(it) }

        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components().addSecuritySchemes(
                    securitySchemeName, SecurityScheme()
                        .name(AUTHORIZATION)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER)
                        .bearerFormat(JWT)
                )
            )
            .servers(servers)
    }
}

package app.concentrate.projectmanagement.configuration

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
class CloudinaryConfig() {
    lateinit var cloudName: String
    lateinit var apiKey: String
    lateinit var apiSecret: String
    @Bean
    fun cloudinary(): Cloudinary {
        return Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
            )
        )
    }
}
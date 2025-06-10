package app.concentrate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties

class ConcentrateApplication

fun main(args: Array<String>) {
	runApplication<ConcentrateApplication>(*args)
}

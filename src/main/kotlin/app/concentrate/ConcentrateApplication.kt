package app.concentrate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcentrateApplication

fun main(args: Array<String>) {
	runApplication<ConcentrateApplication>(*args)
}

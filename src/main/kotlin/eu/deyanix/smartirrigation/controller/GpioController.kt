package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.GpioService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@Tag(name = "GPIO")
@RestController
@ConditionalOnProperty("application.gpio.enabled")
class GpioController(
	private val gpioService: GpioService,
) {
	@GetMapping("/gpio/{pin}")
	@Throws(IOException::class)
	fun state(@PathVariable pin: Int): Boolean? {
		return gpioService.getDigitalOutput(pin)?.isHigh
	}

	@PostMapping("/gpio/{pin}/on")
	@Throws(IOException::class)
	fun on(@PathVariable pin: Int) {
		gpioService.getDigitalOutput(pin)?.high()
	}

	@PostMapping("/gpio/{pin}/off")
	@Throws(IOException::class)
	fun off(@PathVariable pin: Int) {
		gpioService.getDigitalOutput(pin)?.low()
	}

	@PostMapping("/gpio/{pin}/toggle")
	@Throws(IOException::class)
	fun toggle(@PathVariable pin: Int) {
		gpioService.getDigitalOutput(pin)?.toggle()
	}
}

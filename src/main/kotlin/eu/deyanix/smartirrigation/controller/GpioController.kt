package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.GpioService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.time.ZonedDateTime

@Tag(name = "GPIO")
@RestController
class GpioController(
	private val gpioService: GpioService,
) {
	@GetMapping("/gpio/time")
	fun getTime(): ZonedDateTime? {
		return ZonedDateTime.now()
	}

	@GetMapping("/gpio/{pin}")
	@Throws(IOException::class)
	fun state(@PathVariable pin: Int): Boolean? {
		return gpioService.getState(pin)
	}

	@PostMapping("/gpio/{pin}/on")
	@Throws(IOException::class)
	fun on(@PathVariable pin: Int) {
		gpioService.setState(pin, true)
	}

	@PostMapping("/gpio/{pin}/off")
	@Throws(IOException::class)
	fun off(@PathVariable pin: Int) {
		gpioService.setState(pin, false)

	}

	@PostMapping("/gpio/{pin}/toggle")
	@Throws(IOException::class)
	fun toggle(@PathVariable pin: Int) {
		gpioService.toggle(pin)
	}
}

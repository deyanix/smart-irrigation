package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.RaspberryPiService
import eu.deyanix.smartirrigation.service.SenseCapService
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
	private val raspberryPiService: RaspberryPiService,
	private val senseCapService: SenseCapService,
) {
	@GetMapping("/gpio/time")
	fun getTime(): ZonedDateTime? {
		return ZonedDateTime.now()
	}

	@PostMapping("/gpio/migrate")
	fun migrate() {
		senseCapService.migrate()
	}

	@GetMapping("/gpio/{pin}")
	@Throws(IOException::class)
	fun state(@PathVariable pin: Int): Boolean? {
		return raspberryPiService.getState(pin)
	}

	@PostMapping("/gpio/{pin}/on")
	@Throws(IOException::class)
	fun on(@PathVariable pin: Int) {
		raspberryPiService.setState(pin, true)
	}

	@PostMapping("/gpio/{pin}/off")
	@Throws(IOException::class)
	fun off(@PathVariable pin: Int) {
		raspberryPiService.setState(pin, false)

	}

	@PostMapping("/gpio/{pin}/toggle")
	@Throws(IOException::class)
	fun toggle(@PathVariable pin: Int) {
		raspberryPiService.toggle(pin)
	}
}

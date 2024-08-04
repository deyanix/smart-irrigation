package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.SerialReader
import eu.deyanix.smartirrigation.service.SerialService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.*
import java.io.IOException

@Tag(name = "Serial")
@RestController
@ConditionalOnProperty("application.gpio.enabled")
class SerialController(
	private val serialService: SerialService,
	private val serialReader: SerialReader,
) {
	@GetMapping("/serial/read")
	@Throws(IOException::class)
	fun read(): String {
		return serialReader.readLine()
	}

	@GetMapping("/serial/lines")
	@Throws(IOException::class)
	fun nextLines(): Array<String> {
		return serialReader.nextLines()
	}

	@PostMapping("/serial")
	@Throws(IOException::class)
	fun write(@RequestBody text: String) {
		serialService.println(text)
	}
}

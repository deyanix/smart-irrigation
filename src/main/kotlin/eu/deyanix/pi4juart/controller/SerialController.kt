package eu.deyanix.pi4juart.controller

import eu.deyanix.pi4juart.service.SerialReader
import eu.deyanix.pi4juart.service.SerialService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.io.IOException

@Tag(name = "Serial")
@RestController
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

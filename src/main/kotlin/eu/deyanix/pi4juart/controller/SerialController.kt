package eu.deyanix.pi4juart.controller

import eu.deyanix.pi4juart.service.SerialService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.io.IOException

@Tag(name = "Serial")
@RestController
class SerialController(
	private val serialService: SerialService,
) {
	@PostMapping("/serial")
	@Throws(IOException::class)
	fun write(@RequestBody text: String) {
		serialService.println(text)
	}
}

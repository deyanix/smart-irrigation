package eu.deyanix.pi4juart.controller

import com.pi4j.io.serial.Serial
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@RestController
class SerialController @Autowired constructor(
	private var serial: Serial
) {

	@PostMapping("/serial")
	@Throws(IOException::class)
	fun write(@RequestBody text: String) {
		serial.outputStream.write(text.toByteArray())
		serial.outputStream.write('\n'.code)
		serial.outputStream.write('\r'.code)
	}
}

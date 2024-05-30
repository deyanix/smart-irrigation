package eu.deyanix.pi4juart.service

import com.pi4j.io.serial.Serial
import org.springframework.stereotype.Service

@Service
class SerialService(
	private val serial: Serial
) {
	fun print(text: String) {
		serial.outputStream.write(text.toByteArray())
	}

	fun println(text: String) {
		print(text)
		print("\n\r")
	}
}

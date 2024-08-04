package eu.deyanix.smartirrigation.service

import com.pi4j.io.serial.Serial
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty("application.serial.enabled")
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

package eu.deyanix.smartirrigation.configurator

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.serial.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Path

@Configuration
class Pi4jContextConfigurator {
	companion object {
		val DEFAULT_SERIAL_LEGACY: Path = Path.of("/dev/ttyAMA0")
		val DEFAULT_SERIAL: Path = Path.of("/dev/ttyS0")
	}

	private final val pi4j: Context
	private final val serial: Serial

	init {
		this.pi4j = Pi4J.newAutoContext()
		this.serial = pi4j.create(getDefaultSerialConfig())
	}

	private final fun getDefaultSerialPort(): String {
		if (Files.exists(DEFAULT_SERIAL)) {
			return DEFAULT_SERIAL.toString()
		}
		if (Files.exists(DEFAULT_SERIAL_LEGACY)) {
			return DEFAULT_SERIAL_LEGACY.toString()
		}
		throw UnsupportedOperationException("No default serial configuration found")
	}

	private final fun getDefaultSerialConfig(): SerialConfig {
		return Serial.newConfigBuilder(pi4j)
			.baud(Baud._9600)
			.dataBits(DataBits._8)
			.parity(Parity.NONE)
			.stopBits(StopBits._1)
			.flowControl(FlowControl.NONE)
			.provider("pigpio-serial")
			.device(getDefaultSerialPort())
			.build()
	}

	@Bean
	fun getContext(): Context {
		return pi4j
	}

	@Bean
	fun getSerial(): Serial {
		return serial
	}
}

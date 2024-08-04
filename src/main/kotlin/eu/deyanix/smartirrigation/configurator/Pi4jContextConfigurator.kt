package eu.deyanix.smartirrigation.configurator

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.serial.*
import eu.deyanix.smartirrigation.configuration.GpioConfiguration
import eu.deyanix.smartirrigation.configuration.SerialConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Path

@Configuration
class Pi4jContextConfigurator(
	private val serialConfiguration: SerialConfiguration,
	private val gpioConfiguration: GpioConfiguration
) {
	companion object {
		val DEFAULT_SERIAL_LEGACY: Path = Path.of("/dev/ttyAMA0")
		val DEFAULT_SERIAL: Path = Path.of("/dev/ttyS0")

		private fun getDefaultSerialPort(): String {
			if (Files.exists(DEFAULT_SERIAL)) {
				return DEFAULT_SERIAL.toString()
			}
			if (Files.exists(DEFAULT_SERIAL_LEGACY)) {
				return DEFAULT_SERIAL_LEGACY.toString()
			}
			throw UnsupportedOperationException("No default serial configuration found")
		}
	}

	private final var pi4j: Context? = null
	private final var serial: Serial? = null

	init {
		if (serialConfiguration.enabled || gpioConfiguration.enabled)
			this.pi4j = Pi4J.newAutoContext()
		if (serialConfiguration.enabled)
			this.serial = createSerial()
	}

	@Bean
	fun getContext(): Context? {
		return pi4j
	}

	@Bean
	fun getSerial(): Serial? {
		return serial
	}

	private final fun createSerial(): Serial? {
		val serialConfig = Serial.newConfigBuilder(pi4j)
			.baud(Baud.getInstance(serialConfiguration.baud))
			.dataBits(DataBits.getInstance(serialConfiguration.dataBits))
			.parity(Parity.getInstance(serialConfiguration.parity))
			.stopBits(StopBits.getInstance(serialConfiguration.stopBits))
			.flowControl(FlowControl.getInstance(serialConfiguration.flowControl))
			.provider("pigpio-serial")
			.device(serialConfiguration.device ?: getDefaultSerialPort())
			.build()
		return pi4j?.create(serialConfig)
	}
}

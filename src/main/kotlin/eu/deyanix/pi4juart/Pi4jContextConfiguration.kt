package eu.deyanix.pi4juart

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.serial.*
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Pi4jContextConfiguration() {
	private lateinit var pi4j: Context
	private lateinit var serial: Serial

	init {
		this.pi4j = Pi4J.newAutoContext()
		this.serial = pi4j.create(getDefaultSerialConfig())
	}

	private final fun getDefaultSerialConfig(): SerialConfig {
		return Serial.newConfigBuilder(pi4j)
			.baud(Baud._9600)
			.dataBits(DataBits._8)
			.parity(Parity.NONE)
			.stopBits(StopBits._1)
			.flowControl(FlowControl.NONE)
			.id("my-serial")
			.device("/dev/ttyS0")
			.provider(PiGpioSerialProvider::class.java)
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

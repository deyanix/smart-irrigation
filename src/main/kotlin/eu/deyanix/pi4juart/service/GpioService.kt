package eu.deyanix.pi4juart.service

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalOutputProvider
import org.springframework.stereotype.Service

@Service
class GpioService(private val pi4j: Context) {
	fun getDigitalOutput(address: Int): DigitalOutput {
		return pi4j.registry()
			.allByType(DigitalOutput::class.java)
			.values
			.firstOrNull { it.address == address }
			?: pi4j
			.getDigitalOutputProvider<DigitalOutputProvider>()
			.create(address)
	}
}

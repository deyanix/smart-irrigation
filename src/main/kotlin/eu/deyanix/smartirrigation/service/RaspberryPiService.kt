package eu.deyanix.smartirrigation.service

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalOutputProvider
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException

@Service
class RaspberryPiService(private val pi4j: Context?) {
	private val mock: MutableMap<Int, Boolean> = mutableMapOf()

	fun getState(address: Int): Boolean {
		val gpio = getDigitalOutput(address)
		if (gpio != null)
			return gpio.isHigh
		return mock[address] ?: false
	}

	fun setState(address: Int, state: Boolean) {
		val gpio = getDigitalOutput(address)
		if (gpio != null) {
			gpio.setState(state)
		} else {
			mock[address] = state
		}
	}

	fun toggle(address: Int) {
		setState(address, !getState(address))
	}

	fun getTemperature(): Double? {
		val filePath = "/sys/class/thermal/thermal_zone0/temp"
		try {
			val temperatureString = File(filePath).readText().trim()
			return temperatureString.toDouble() / 1000.0
		} catch (e: IOException) {
			return null
		}
	}

	private fun getDigitalOutput(address: Int): DigitalOutput? {
		return pi4j
			?.registry()
			?.allByType(DigitalOutput::class.java)
			?.values
			?.firstOrNull { it.address == address }
			?: pi4j
				?.getDigitalOutputProvider<DigitalOutputProvider>()
				?.create(address)
	}
}

package eu.deyanix.smartirrigation.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.serial")
class SerialConfiguration {
	var enabled: Boolean = false
	var device: String? = null
	var baud: Int = 9600
	var dataBits: Int = 8
	var parity: Int = 0
	var stopBits: Int = 1
	var flowControl: Int = 0
}

package eu.deyanix.smartirrigation.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.gpio")
class GpioConfiguration {
	var enabled: Boolean = false
	var excluded: Array<Int>? = null
	var included: Array<Int>? = null
}

package eu.deyanix.smartirrigation.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.ttn")
class TtnConfiguration {
	var enabled: Boolean = false
	var applicationId: String? = null
	var tenantId: String = "ttn"
	var mqttServers: List<String> = ArrayList()
	var mqttPassword: String? = null

	val mqttUsername: String
		get() { return "$applicationId@$tenantId" }
}

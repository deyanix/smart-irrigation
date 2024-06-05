package eu.deyanix.smartirrigation.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("ttn")
class TtnConfiguration {
	lateinit var applicationId: String
	var tenantId: String = "ttn"
	lateinit var mqttServers: List<String>
	lateinit var mqttPassword: String

	val mqttUsername: String
		get() { return "$applicationId@$tenantId" }
}

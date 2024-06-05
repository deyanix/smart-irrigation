package eu.deyanix.smartirrigation.configurator

import eu.deyanix.smartirrigation.configuration.TtnConfiguration
import org.apache.commons.lang3.stream.IntStreams
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageProducer
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

@Configuration
class MqttConfigurator(
	private val ttnConfiguration: TtnConfiguration
) {
	private final val logger: Logger = LoggerFactory.getLogger(MqttConfigurator::class.java)

	@Bean
	fun clientFactory(): MqttPahoClientFactory {
		val options = MqttConnectOptions()
		options.isCleanSession = true
		options.serverURIs = ttnConfiguration.mqttServers.toTypedArray()
		options.userName = ttnConfiguration.mqttUsername
		options.password = ttnConfiguration.mqttPassword.toCharArray()

		val factory = DefaultMqttPahoClientFactory()
		factory.connectionOptions = options

		logger.info("Configured MQTT client factory")
		return factory
	}

	@Bean
	fun mqttInputChannel(): MessageChannel {
		logger.info("Configured MQTT input channel")
		return DirectChannel()
	}

	@Bean
	fun inbound(): MessageProducer {
		val adapter = MqttPahoMessageDrivenChannelAdapter("TTN", clientFactory())
		adapter.addTopic("v3/" + ttnConfiguration.mqttUsername + "/devices/+/up", 0)
		adapter.setCompletionTimeout(5000)
		adapter.setConverter(DefaultPahoMessageConverter())
		adapter.setQos(0)
		adapter.outputChannel = mqttInputChannel()
		logger.info("Configured MQTT channel adapter")
		return adapter
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	fun handler(): MessageHandler {
		return MessageHandler {
			val json = JSONObject(it.payload.toString())

			val deviceId = json
				.getJSONArray("identifiers")
				.getJSONObject(0)
				.getJSONObject("device_ids")
				.getString("deviceId")

			val messages = json.getJSONObject("data")
				.getJSONObject("uplink_message")
				.getJSONObject("decoded_payload")
				.getJSONArray("messages")

			IntStreams.range(messages.length())
				.mapToObj { index -> messages.getJSONObject(index) }
				.forEach {
					msg -> logger.info("Sensor ("+ deviceId + "): " + msg.getInt("measurementId") + " = " + msg.getDouble("measurementValue"))
				}

			logger.info("Message (" + it.javaClass + "): " + it.payload.toString())
			logger.info("Message headers: " + it.headers)
		}
	}
}

package eu.deyanix.smartirrigation.configurator

import com.pi4j.Pi4J
import com.pi4j.context.Context
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty("application.gpio.enabled")
class Pi4jContextConfigurator {
	private final var pi4j: Context? = null

	init {
		this.pi4j = Pi4J.newAutoContext()
	}

	@Bean
	fun getContext(): Context? {
		return pi4j
	}
}

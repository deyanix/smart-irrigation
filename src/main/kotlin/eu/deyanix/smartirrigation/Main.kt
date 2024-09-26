package eu.deyanix.smartirrigation

import eu.deyanix.smartirrigation.configuration.GpioConfiguration
import eu.deyanix.smartirrigation.configuration.TtnConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(
	TtnConfiguration::class,
	GpioConfiguration::class,
)
class Main

fun main(args: Array<String>) {
	runApplication<Main>(*args)
}

package eu.deyanix.smartirrigation

import eu.deyanix.smartirrigation.service.IrrigationService
import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SectionInitializer(
	private val irrigationService: IrrigationService
) {
	@PostConstruct
	fun init() {
		irrigationService.resetWithGpio()
	}

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	fun reportCurrentTime() {
		irrigationService.synchronizeWithGpio()
	}
}

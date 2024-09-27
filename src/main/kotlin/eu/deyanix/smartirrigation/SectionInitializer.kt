package eu.deyanix.smartirrigation

import eu.deyanix.smartirrigation.service.SectionValveService
import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SectionInitializer(private val sectionValveService: SectionValveService) {
	@PostConstruct
	fun init() {
		sectionValveService.resetWithGpio()
	}

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	fun reportCurrentTime() {
		sectionValveService.synchronizeGpio()
	}
}

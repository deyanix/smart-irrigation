package eu.deyanix.smartirrigation

import eu.deyanix.smartirrigation.service.SectionService
import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class SectionInitializer(private val sectionService: SectionService) {
	@PostConstruct
	fun init() {
		sectionService.resetWithGpio()
	}

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	fun reportCurrentTime() {
		sectionService.synchronizeGpio()
	}
}

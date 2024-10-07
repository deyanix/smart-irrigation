package eu.deyanix.smartirrigation

import eu.deyanix.smartirrigation.dao.Measurement
import eu.deyanix.smartirrigation.repository.MeasurementRepository
import eu.deyanix.smartirrigation.repository.SensorItemRepository
import eu.deyanix.smartirrigation.repository.SensorRepository
import eu.deyanix.smartirrigation.service.RaspberryPiService
import eu.deyanix.smartirrigation.service.SectionValveService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import kotlin.jvm.optionals.getOrNull

@Component
class MainScheduler(
	private val sectionValveService: SectionValveService,
	private val raspberryPiService: RaspberryPiService,
	private val sensorRepository: SensorRepository,
	private val sensorItemRepository: SensorItemRepository,
	private val measurementRepository: MeasurementRepository,
) {
	@PostConstruct
	fun init() {
		sectionValveService.resetWithGpio()
	}

	@PreDestroy
	fun destroy() {
		sectionValveService.resetWithGpio(true)
	}

	@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
	fun reportCurrentTime() {
		sectionValveService.synchronizeGpio()
	}

	@Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
	fun reportRaspberryPiTemperature() {
		val value = raspberryPiService.getTemperature()
			?: return
		val sensor = sensorRepository.findByType("RaspberryPi")
			.getOrNull() ?: return
		val sensorItem = sensorItemRepository.findByKey(sensor, "temperature")
			.getOrNull() ?: return

		measurementRepository.saveAndFlush(Measurement(
			sensorItem = sensorItem,
			date = OffsetDateTime.now(),
			value = value,
		))
	}
}

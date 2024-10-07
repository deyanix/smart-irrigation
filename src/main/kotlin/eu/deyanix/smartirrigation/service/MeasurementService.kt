package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.MeasurementCriteria
import eu.deyanix.smartirrigation.dto.MeasurementItem
import eu.deyanix.smartirrigation.repository.MeasurementRepository
import eu.deyanix.smartirrigation.repository.SensorItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.OffsetDateTime

@Service
class MeasurementService(
	private val sensorItemRepository: SensorItemRepository,
	private val measurementRepository: MeasurementRepository
) {
	@Transactional
	fun getMeasurements(sensorItemId: Int, criteria: MeasurementCriteria): List<MeasurementItem> {
		val sensorItem = sensorItemRepository.findById(sensorItemId)
			.orElseThrow()

		val dateTo = criteria.dateTo ?: OffsetDateTime.now()
		val dateFrom = criteria.dateFrom ?: dateTo.plusDays(30)

		val duration = Duration.between(dateFrom, dateTo)

		return when {
			duration.toDays() >= 730 -> measurementRepository.findAllDaily(sensorItem, dateFrom, dateTo)
				.toList()
			duration.toDays() >= 30 -> measurementRepository.findAllHourly(sensorItem, dateFrom, dateTo)
				.toList()
			else -> measurementRepository.findAllMinutely(sensorItem, dateFrom, dateTo)
				.toList()
		}
	}
}

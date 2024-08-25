package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dto.SectionSummaryDTO
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.utils.LocalDateTimes
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Collectors

@Service
class IrrigationService(
	private val irrigationRepository: IrrigationRepository,
	private val sectionValveService: SectionValveService,
) {
	@Transactional
	fun synchronizeWithGpio() {
		irrigationRepository.findAllUnfinished()
			.toList()
			.groupBy(Irrigation::section)
			.forEach { (section, irrigations) ->
				val state = sectionValveService.isOpen(section)
				irrigations.forEach {
					if (state) {
						it.end = LocalDateTime.now()
					} else {
						it.finished = true
					}
				}
				irrigationRepository.saveAll(irrigations)
			}
		irrigationRepository.flush()
	}

	@Transactional(readOnly = true)
	fun summarize(installationId: Int, from: LocalDate, to: LocalDate): Iterable<SectionSummaryDTO> {
		val fromDateTime = from.atStartOfDay()
		val toDateTime = to.atTime(LocalTime.MAX)

		return irrigationRepository.findAllBetween(installationId, fromDateTime, toDateTime)
			.map {
				it.start = LocalDateTimes.minmax(it.start, fromDateTime, toDateTime)
				it.end = LocalDateTimes.minmax(it.end, fromDateTime, toDateTime)
				it
			}
			.collect(Collectors.groupingBy { it.section })
			.map { (section, irrigations) ->
				val totalDuration = irrigations
					.map { Duration.between(it.start, it.end) }
					.fold(Duration.ZERO, Duration::plus)

				return@map SectionSummaryDTO(
					id = section.id,
					name = section.name,
					durationSeconds = totalDuration.toSeconds())
			}
	}
}



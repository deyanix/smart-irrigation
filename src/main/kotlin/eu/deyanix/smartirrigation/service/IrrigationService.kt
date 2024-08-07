package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dto.SectionSummaryDTO
import eu.deyanix.smartirrigation.repository.IrrigationRepository
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
	private val gpioService: GpioService,
) {
	fun refresh(installationId: Int) {
		irrigationRepository.findAllUnfinished(installationId)
			.groupBy(Irrigation::section)
			.forEach {(installationSection, irrigations) ->
				val state = gpioService.getState(installationSection.gpio)
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
				it.start = it.start.minmax(fromDateTime, toDateTime)
				it.end = it.end.minmax(fromDateTime, toDateTime)
				it
			}
			.collect(Collectors.groupingBy { it.section })
			.map { (section, irrigations) ->
				val totalDuration = irrigations
					.map { Duration.between(it.start, it.end) }
					.fold(Duration.ZERO, Duration::plus)
				SectionSummaryDTO(section.id, section.name, totalDuration.toSeconds())
			}
	}
}

fun LocalDateTime.minmax(from: LocalDateTime, to: LocalDateTime): LocalDateTime {
	if (isAfter(to)) {
		return to
	} else if (isBefore(from)) {
		return from
	}
	return this
}

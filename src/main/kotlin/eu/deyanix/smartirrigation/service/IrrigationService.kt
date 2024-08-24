package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dto.SectionSummaryDTO
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.utils.LocalDateTimeExtensions.minmax
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.stream.Collectors

@Service
class IrrigationService(
	private val sectionRepository: SectionRepository,
	private val irrigationRepository: IrrigationRepository,
	private val gpioService: GpioService,
) {
	fun start(installationId: Int, sectionIndex: Int) {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		val irrigation = irrigationRepository.findUnfinishedBySection(section)
			.orElseGet { Irrigation(section = section) }
			.apply { end = LocalDateTime.now() }

		irrigationRepository.saveAndFlush(irrigation)
		gpioService.setState(section.gpio, true)
	}

	@Transactional(readOnly = true)
	fun refresh(installationId: Int) {
		irrigationRepository.findAllUnfinished(installationId)
			.collect(Collectors.toList())
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

				return@map SectionSummaryDTO(
					id = section.id,
					name = section.name,
					durationSeconds = totalDuration.toSeconds())
			}
	}
}



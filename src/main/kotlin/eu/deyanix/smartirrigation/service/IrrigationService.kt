package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.IrrigationSpan
import eu.deyanix.smartirrigation.dto.IrrigationSpans
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import eu.deyanix.smartirrigation.repository.SectionSlotRepository
import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class IrrigationService(
	private val irrigationRepository: IrrigationRepository,
	private val sectionValveService: SectionValveService,
	private val sectionRepository: SectionRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val sectionSlotRepository: SectionSlotRepository,
) {
	fun getSlotSpans(section: Section, dateFrom: LocalDateTime) =
		sectionSlotRepository.findAllBySection(section)
			.flatMap { slot ->
				val builder = IrrigationSpan.Builder(
					start = slot.start,
					end = slot.end,
					state = true,
				)

				return@flatMap slot.weekdays
					.stream()
					.map { builder.next(dateFrom, DayOfWeek.of(it.weekday)) }
			}
			.toList()

	@Transactional
	fun getScheduleSpans(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?) =
		sectionScheduleRepository.findAllBySectionBetween(section, dateFrom, dateTo, state)
			.map {
				IrrigationSpan(
					span = LocalTimeSpan(start = it.start, end = it.end),
					state = it.state,
				)
			}
			.toList()

	@Transactional(readOnly = true)
	fun getUpcomingIrrigations(section: Section): List<LocalTimeSpan> {
		val now = LocalDateTime.now()
		val localMin = now.minusDays(1).with(LocalTime.MIN)
		val localMax = now.plusDays(7).with(LocalTime.MAX)

		val sectionSlotSpans = IrrigationSpans.of(
			getSlotSpans(section, now))

		val schedulePositiveSpans = IrrigationSpans.of(
			getScheduleSpans(
				section,
				listOfNotNull(sectionSlotSpans.minTime(), localMin).min(),
				listOfNotNull(sectionSlotSpans.maxTime(), localMax).max(),
				true))

		val allPositiveSpans = IrrigationSpans.flat(listOf(sectionSlotSpans, schedulePositiveSpans))

		val scheduleNegativeSpans = IrrigationSpans.of(
			getScheduleSpans(
				section,
				listOfNotNull(allPositiveSpans.minTime(), localMin).min(),
				listOfNotNull(allPositiveSpans.maxTime(), localMax).max(),
				false))

		val mergedSpans = IrrigationSpans.flat(listOf(allPositiveSpans, scheduleNegativeSpans))
		if (mergedSpans.state) {
			return mergedSpans.spans
				.sortedBy { it.start }
		}
		return emptyList()
	}

	@Transactional(readOnly = true)
	fun getUpcomingIrrigations(sectionId: Int): List<LocalTimeSpan> {
		return getUpcomingIrrigations(sectionRepository.findById(sectionId).orElseThrow())
	}

	@Transactional(readOnly = true)
	fun getCurrentIrrigations(section: Section): List<LocalTimeSpan> {
		val now = LocalDateTime.now()
		return getUpcomingIrrigations(section)
			.filter { span -> span.isBetween(now) }
	}

	@Transactional(readOnly = true)
	fun getCurrentIrrigations(sectionId: Int): List<LocalTimeSpan> {
		return getCurrentIrrigations(sectionRepository.findById(sectionId).orElseThrow())
	}

	fun start(section: Section) {
		sectionValveService.setOpen(section, true)

		val irrigations = irrigationRepository.findAllUnfinishedBySection(section).toList().toMutableList()
		if (irrigations.any()) {
			irrigations.forEach { it.finished = true }
		} else {
			irrigations.add(Irrigation(section = section))
		}

		irrigations.first().apply {
			finished = false
			end = LocalDateTime.now()
		}

		irrigationRepository.saveAllAndFlush(irrigations)
	}

	fun stop(section: Section) {
		sectionValveService.setOpen(section, false)
		val irrigations = irrigationRepository.findAllUnfinishedBySection(section).toList()
		irrigations.forEach { it.finished = true }

		irrigationRepository.saveAllAndFlush(irrigations)
	}

	@Transactional
	fun resetWithGpio() {
		sectionRepository.findAll()
			.forEach { section ->
				sectionValveService.setOpen(section, false)
				stop(section)
			}
	}

	@Transactional
	fun synchronizeWithGpio() {
		sectionRepository.findAll()
			.forEach { section ->
				val shouldState = getCurrentIrrigations(section).any()

				if (shouldState) {
					start(section)
				} else {
					stop(section)
				}
			}
	}
}



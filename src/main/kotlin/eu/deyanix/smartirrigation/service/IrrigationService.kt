package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.IrrigationSpan
import eu.deyanix.smartirrigation.dto.IrrigationSpans
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
	private val sectionRepository: SectionRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val sectionSlotRepository: SectionSlotRepository,
) {
	fun getSlotSpans(section: Section, dateFrom: LocalDateTime): MutableList<IrrigationSpan> =
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
	fun getScheduleSpans(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?): MutableList<IrrigationSpan> =
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

		val allPositiveSpans = IrrigationSpans.flatten(listOf(sectionSlotSpans, schedulePositiveSpans))

		val scheduleNegativeSpans = IrrigationSpans.of(
			getScheduleSpans(
				section,
				listOfNotNull(allPositiveSpans.minTime(), localMin).min(),
				listOfNotNull(allPositiveSpans.maxTime(), localMax).max(),
				false))

		val mergedSpans = IrrigationSpans.flatten(listOf(allPositiveSpans, scheduleNegativeSpans))
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
}



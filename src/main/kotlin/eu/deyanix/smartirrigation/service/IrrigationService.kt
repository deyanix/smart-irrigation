package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.IrrigationDTO
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
import java.util.stream.Stream

@Service
class IrrigationService(
	private val sectionRepository: SectionRepository,
	private val sectionSlotRepository: SectionSlotRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val irrigationRepository: IrrigationRepository,
) {
	@Transactional(readOnly = true)
	fun getIrrigations(sectionId: Int): Stream<IrrigationDTO> {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		return irrigationRepository.findAllBetweenBySection(section, LocalDateTime.now().minusDays(1), LocalDateTime.now())
			.map(::IrrigationDTO)
			.sorted(Comparator.comparing(IrrigationDTO::start).reversed())
	}

	@Transactional(readOnly = true)
	fun getSlotSpans(section: Section, dateFrom: LocalDateTime): IrrigationSpans {
		val spans = sectionSlotRepository.findAllBySection(section)
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

		return IrrigationSpans.of(spans)
	}

	@Transactional(readOnly = true)
	fun getSlotSpansInTime(section: Section, dateFrom: LocalDateTime, dateTo: LocalDateTime): IrrigationSpans {
		return this.getSlotSpans(section, dateFrom).onlyWhen(dateFrom, dateTo)
	}

	@Transactional(readOnly = true)
	fun getScheduleSlots(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?): IrrigationSpans {
		val spans = sectionScheduleRepository.findAllBySectionBetween(section, dateFrom, dateTo, state)
			.map {
				IrrigationSpan(
					span = LocalTimeSpan(start = it.start, end = it.end),
					state = it.state,
				)
			}
			.toList()

		return IrrigationSpans.of(spans)
	}

	@Transactional(readOnly = true)
	fun getUpcomingIrrigations(section: Section): List<LocalTimeSpan> {
		val now = LocalDateTime.now()
		val localMin = now.minusDays(1).with(LocalTime.MIN)
		val localMax = now.plusDays(7).with(LocalTime.MAX)

		val sectionSlotSpans = this.getSlotSpans(section, now)
		val schedulePositiveSpans = getScheduleSlots(
			section,
			listOfNotNull(sectionSlotSpans.minTime(), localMin).min(),
			listOfNotNull(sectionSlotSpans.maxTime(), localMax).max(),
			true)
		val allPositiveSpans = IrrigationSpans.flatten(listOf(sectionSlotSpans, schedulePositiveSpans))
		val scheduleNegativeSpans = getScheduleSlots(
			section,
			listOfNotNull(allPositiveSpans.minTime(), localMin).min(),
			listOfNotNull(allPositiveSpans.maxTime(), localMax).max(),
			false)

		val mergedSpans = IrrigationSpans.flatten(listOf(allPositiveSpans, scheduleNegativeSpans))
		if (mergedSpans.state) {
			return mergedSpans.spans
				.filter { it.end > now }
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
}



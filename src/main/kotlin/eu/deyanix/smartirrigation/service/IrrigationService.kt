package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Installation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.*
import eu.deyanix.smartirrigation.repository.*
import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import eu.deyanix.smartirrigation.utils.LocalTimeSpanBuilder
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class IrrigationService(
	private val sectionRepository: SectionRepository,
	private val sectionSlotRepository: SectionSlotRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val irrigationRepository: IrrigationRepository,
	private val installationRepository: InstallationRepository,
) {
	@Transactional(readOnly = true)
	fun searchByInstallation(installationId: Int, criteria: IrrigationCriteria): SearchResponse<IrrigationDTO> {
		val section = installationRepository.findById(installationId)
			.orElseThrow()

		val result = irrigationRepository
			.findPageByInstallationBetween(
				section,
				criteria.from,
				criteria.to,
				PageRequest.of(criteria.page, criteria.pageSize)
			)
			.map(::IrrigationDTO)

		return SearchResponse(result)
	}

	@Transactional(readOnly = true)
	fun searchBySection(sectionId: Int, criteria: IrrigationCriteria): SearchResponse<IrrigationDTO> {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val result = irrigationRepository
			.findPageBySectionBetween(
				section,
				criteria.from,
				criteria.to,
				PageRequest.of(criteria.page, criteria.pageSize)
			)
			.map(::IrrigationDTO)

		return SearchResponse(result)
	}

	@Transactional(readOnly = true)
	fun getSlotSpans(section: Section, dateFrom: LocalDateTime): IrrigationSpanCollection {
		val spans = sectionSlotRepository.findAllBySection(section)
			.flatMap { slot ->
				val builder = LocalTimeSpanBuilder(
					ref = dateFrom,
					start = slot.start,
					end = slot.end,
				)

				return@flatMap slot.weekdays
					.stream()
					.map {
						val timeSpan = builder.next(DayOfWeek.of(it.weekday))
						return@map IrrigationSpan(
							timeSpan = timeSpan,
							state = true,
							sources = arrayOf(
								IrrigationSource.SectionSlotSource(slot, timeSpan.start, timeSpan.end)
							)
						)
					}
			}
			.toList()

		return IrrigationSpanCollection.ofSpans(spans)
	}

	@Transactional(readOnly = true)
	fun getSlotSpansInTime(section: Section, dateFrom: LocalDateTime, dateTo: LocalDateTime): IrrigationSpanCollection {
		return getSlotSpans(section, dateFrom).onlyWhen(dateFrom, dateTo)
	}

	@Transactional(readOnly = true)
	fun getScheduleSlots(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?): IrrigationSpanCollection {
		val spans = sectionScheduleRepository.findAllBySectionInTime(section, dateFrom, dateTo, state)
			.map {
				IrrigationSpan(
					timeSpan = LocalTimeSpan(start = it.start, end = it.end),
					state = it.state,
					sources = arrayOf(IrrigationSource.SectionScheduleSource(it))
				)
			}
			.toList()

		return IrrigationSpanCollection.ofSpans(spans)
	}

	@Transactional(readOnly = true)
	fun getUpcomingIrrigationsBySection(section: Section): IrrigationSpanCollection {
		val now = LocalDateTime.now()
		val localMin = now.minusDays(1).with(LocalTime.MIN)
		val localMax = now.plusDays(7).with(LocalTime.MAX)

		val sectionSlotSpans = getSlotSpans(section, now)
		val schedulePositiveSpans = getScheduleSlots(section, localMin, localMax, true)
		val scheduleNegativeSpans = getScheduleSlots(section, localMin, localMax, false)

		return IrrigationSpanCollection.ofCollections(sectionSlotSpans, schedulePositiveSpans, scheduleNegativeSpans)
			.onlyState(true)
			.onlyAfter(now)
	}

	@Transactional(readOnly = true)
	fun getUpcomingIrrigationsBySection(sectionId: Int): List<UpcomingIrrigationItem> =
		getUpcomingIrrigationsBySection(sectionRepository.findById(sectionId).orElseThrow())
			.spans
			.map { it.toResponse() }

	@Transactional(readOnly = true)
	fun getUpcomingIrrigationsByInstallation(installation: Installation): IrrigationSpanCollection {
		val spanCollections = sectionRepository.findAllByInstallation(installation)
			.map {getUpcomingIrrigationsBySection(it)}
			.toList()

		return IrrigationSpanCollection.ofCollections(spanCollections)
	}

	@Transactional(readOnly = true)
	fun getUpcomingIrrigationsByInstallation(installationId: Int): List<UpcomingIrrigationItem> =
		getUpcomingIrrigationsByInstallation(installationRepository.findById(installationId).orElseThrow())
			.spans
			.map { it.toResponse() }
}

package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.dto.SectionSlotListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotOccurrenceDTO
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import eu.deyanix.smartirrigation.repository.SectionSlotRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.stream.Stream
import kotlin.Comparator

@Service
class SectionService(
	private val sectionRepository: SectionRepository,
	private val sectionSlotRepository: SectionSlotRepository,
	private val sectionValveService: SectionValveService,
	private val irrigationRepository: IrrigationRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
) {
	@Transactional(readOnly = true)
	fun getSections(installationId: Int): Stream<SectionDTO> =
		sectionRepository.findAllByInstallation(installationId)
			.map(this::convertSectionToDTO)

	@Transactional(readOnly = true)
	fun getSlots(installationId: Int, sectionIndex: Int): Stream<SectionSlotListDTO> {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		return sectionSlotRepository.findAllBySection(section)
			.map(::SectionSlotListDTO)
	}

	@Transactional(readOnly = true)
	fun getUpcomingSlots(installationId: Int, sectionIndex: Int): Stream<SectionSlotOccurrenceDTO> {
		val now = LocalDateTime.now()
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		val schedules = sectionScheduleRepository.findAllBySectionUntil(section, LocalDate.now().plusWeeks(1).atTime(LocalTime.MAX))
			.map {
				SectionSlotOccurrenceDTO(
					start = it.start,
					end = it.end,
					source = if (it.state) SectionSlotOccurrenceDTO.Source.SCHEDULE
						else SectionSlotOccurrenceDTO.Source.SCHEDULE_SHUTDOWN,
				)
			}
			.toList()

		val sectionSlotsStream = sectionSlotRepository.findAllBySection(section)
			.flatMap { slot ->
				slot.weekdays.stream().map {
					SectionSlotOccurrenceDTO.Builder(
						weekday = DayOfWeek.of(it.weekday),
						start = slot.start,
						end = slot.end,
						source = SectionSlotOccurrenceDTO.Source.SLOT,
					).next(now)
				}
			}

		val scheduleSlots = schedules.filter { it.source == SectionSlotOccurrenceDTO.Source.SCHEDULE }

		return Stream.concat(sectionSlotsStream, scheduleSlots.stream())
			.map { slot ->
				val span = schedules
					.stream()
					.filter { it.source == SectionSlotOccurrenceDTO.Source.SCHEDULE_SHUTDOWN && it.span.isOverlap(slot.span) }
					.map { it.span }
					.reduce(slot.span) { s1, s2 -> s1?.intersection(s2) }

				return@map if (span != null) SectionSlotOccurrenceDTO(span, slot.source)
					else null!!
			}
			.filter(Objects::nonNull)
			.sorted(Comparator.comparing(SectionSlotOccurrenceDTO::start))
	}

	@Transactional(readOnly = true)
	fun getCurrentSlots(installationId: Int, sectionIndex: Int): Stream<SectionSlotOccurrenceDTO> {
		val now = LocalDateTime.now()
		return getUpcomingSlots(installationId, sectionIndex)
			.filter { slot -> slot.span.isBetween(now) }
	}

	@Transactional(readOnly = true)
	fun getIrrigations(installationId: Int, sectionIndex: Int): Stream<IrrigationDTO> {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		return irrigationRepository.findAllBetweenBySection(section, LocalDateTime.now().minusDays(1), LocalDateTime.now())
			.map(::IrrigationDTO)
			.sorted(Comparator.comparing(IrrigationDTO::start).reversed())
	}

	fun start(installationId: Int, sectionIndex: Int) {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		val irrigation = irrigationRepository.findUnfinishedBySection(section)
			.orElseGet { Irrigation(section = section) }
			.apply { end = LocalDateTime.now() }

		sectionValveService.setOpen(section, true)
		irrigationRepository.saveAndFlush(irrigation)
	}

	fun stop(installationId: Int, sectionIndex: Int) {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		sectionValveService.setOpen(section, false)
		irrigationRepository.findUnfinishedBySection(section)
			.ifPresent {
				it.end = LocalDateTime.now()
				irrigationRepository.saveAndFlush(it)
			}
	}

	private fun convertSectionToDTO(section: Section): SectionDTO =
		SectionDTO(
			section = section,
			irrigating = sectionValveService.isOpen(section))
}

package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Installation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.repository.InstallationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.stream.Stream

@Service
class SectionService(
	private val installationRepository: InstallationRepository,
	private val sectionRepository: SectionRepository,
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val irrigationService: IrrigationService,
	private val sectionValveService: SectionValveService,
	private val sectionScheduleService: SectionScheduleService,
) {
	@Transactional(readOnly = true)
	fun getSection(sectionId: Int): SectionDTO =
		sectionRepository.findById(sectionId)
			.map(this::convertSectionToDTO)
			.orElseThrow()

	@Transactional(readOnly = true)
	fun getSections(installation: Installation): Stream<SectionDTO> =
		sectionRepository.findAllByInstallation(installation)
			.map(this::convertSectionToDTO)

	@Transactional(readOnly = true)
	fun getSections(installationId: Int): Stream<SectionDTO> =
		getSections(installationRepository.findById(installationId).orElseThrow())

	@Transactional
	fun stop(section: Section, dateFrom: LocalDateTime, dateTo: LocalDateTime) {
		sectionScheduleService.ensureSchedule(SectionSchedule(
			section = section,
			start = dateFrom,
			end = dateTo,
			state = false,
		))

		val currentSlotSpans = irrigationService.getSlotSpansInTime(section, dateFrom, dateTo)
		val stopSchedules = currentSlotSpans.spans
			.map {
				SectionSchedule(
					section = section,
					start = it.timeSpan.start,
					end = it.timeSpan.end,
					state = false,
				)
			}

		sectionScheduleRepository.saveAllAndFlush(stopSchedules)
		sectionValveService.synchronizeGpio(section)
	}

	@Transactional
	fun stop(sectionId: Int) {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val now = LocalDateTime.now()
		stop(section, now, now)
	}

	private fun convertSectionToDTO(section: Section): SectionDTO =
		SectionDTO(
			section = section,
			irrigating = sectionValveService.isOpen(section))
}

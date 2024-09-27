package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.stream.Stream

@Service
class SectionService(
	private val sectionRepository: SectionRepository,
	private val sectionValveService: SectionValveService,
	private val irrigationRepository: IrrigationRepository,
	private val irrigationService: IrrigationService,
) {
	@Transactional(readOnly = true)
	fun getSection(sectionId: Int): SectionDTO =
		sectionRepository.findById(sectionId)
			.map(this::convertSectionToDTO)
			.orElseThrow()

	@Transactional(readOnly = true)
	fun getSections(installationId: Int): Stream<SectionDTO> =
		sectionRepository.findAllByInstallation(installationId)
			.map(this::convertSectionToDTO)

	@Transactional(readOnly = true)
	fun getIrrigations(sectionId: Int): Stream<IrrigationDTO> {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		return irrigationRepository.findAllBetweenBySection(section, LocalDateTime.now().minusDays(1), LocalDateTime.now())
			.map(::IrrigationDTO)
			.sorted(Comparator.comparing(IrrigationDTO::start).reversed())
	}

	@Transactional
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

	@Transactional
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
	fun synchronizeGpio(section: Section) {
		val shouldState = irrigationService.getCurrentIrrigations(section).isNotEmpty()

		if (shouldState) {
			start(section)
		} else {
			stop(section)
		}
	}

	@Transactional
	fun synchronizeGpio() {
		sectionRepository.findAll()
			.forEach { section ->
				synchronizeGpio(section)
			}
	}

	private fun convertSectionToDTO(section: Section): SectionDTO =
		SectionDTO(
			section = section,
			irrigating = sectionValveService.isOpen(section))
}

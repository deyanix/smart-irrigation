package eu.deyanix.smartirrigation.service

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
) {
	@Transactional(readOnly = true)
	fun getSections(installationId: Int): Stream<SectionDTO> =
		sectionRepository.findAllByInstallation(installationId)
			.map(this::convertSectionToDTO)

	@Transactional(readOnly = true)
	fun getIrrigations(installationId: Int, sectionIndex: Int): Stream<IrrigationDTO> {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		return irrigationRepository.findAllBetweenBySection(section, LocalDateTime.now().minusDays(1), LocalDateTime.now())
			.map(::IrrigationDTO)
			.sorted(Comparator.comparing(IrrigationDTO::start).reversed())
	}

	private fun convertSectionToDTO(section: Section): SectionDTO =
		SectionDTO(
			section = section,
			irrigating = sectionValveService.isOpen(section))
}

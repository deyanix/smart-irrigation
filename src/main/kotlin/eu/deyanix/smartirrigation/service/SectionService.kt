package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.SectionListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotOccurrenceDTO
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionSlotRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDateTime

@Service
class SectionService(
	private val sectionRepository: SectionRepository,
	private val sectionSlotRepository: SectionSlotRepository,
) {
	@Transactional(readOnly = true)
	fun getSections(installationId: Int): List<SectionListDTO> =
		sectionRepository.findAllByInstallation(installationId)
			.map(::SectionListDTO)
			.toList()

	@Transactional(readOnly = true)
	fun getWindows(installationId: Int, sectionIndex: Int): List<SectionSlotListDTO> =
		sectionSlotRepository.findAllBySection(installationId, sectionIndex)
			.map(::SectionSlotListDTO)
			.toList()

	@Transactional(readOnly = true)
	fun getUpcomingWindows(installationId: Int, sectionIndex: Int): List<SectionSlotOccurrenceDTO> {
		val now = LocalDateTime.now()

		return sectionSlotRepository.findAllBySection(installationId, sectionIndex)
			.flatMap { window ->
				window.weekdays.stream().map {
					SectionSlotOccurrenceDTO.Builder(
						DayOfWeek.of(it.weekday),
						window.start,
						window.end
					).next(now)
				}
			}
			.sorted(Comparator.comparing(SectionSlotOccurrenceDTO::start))
			.toList()
	}
}

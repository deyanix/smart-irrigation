package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.SectionListDTO
import eu.deyanix.smartirrigation.dto.SectionWindowListDTO
import eu.deyanix.smartirrigation.dto.SectionWindowOccurrenceDTO
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionWindowRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDateTime

@Service
class SectionService(
	private val sectionRepository: SectionRepository,
	private val sectionWindowRepository: SectionWindowRepository,
) {
	@Transactional(readOnly = true)
	fun getSections(installationId: Int): List<SectionListDTO> =
		sectionRepository.findAllByInstallation(installationId)
			.map(::SectionListDTO)
			.toList()

	@Transactional(readOnly = true)
	fun getWindows(installationId: Int, sectionIndex: Int): List<SectionWindowListDTO> =
		sectionWindowRepository.findAllBySection(installationId, sectionIndex)
			.map(::SectionWindowListDTO)
			.toList()

	@Transactional(readOnly = true)
	fun getUpcomingWindows(installationId: Int, sectionIndex: Int): List<SectionWindowOccurrenceDTO> {
		val now = LocalDateTime.now()

		return sectionWindowRepository.findAllBySection(installationId, sectionIndex)
			.flatMap { window ->
				window.weekdays.stream().map {
					SectionWindowOccurrenceDTO.Builder(
						DayOfWeek.of(it.weekday),
						window.start,
						window.end
					).next(now)
				}
			}
			.sorted(Comparator.comparing(SectionWindowOccurrenceDTO::start))
			.toList()
	}
}

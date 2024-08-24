package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotOccurrenceDTO
import eu.deyanix.smartirrigation.service.SectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class SectionController(
	private val installationSectionService: SectionService,
) {
	@GetMapping("/installations/{installationId}/sections")
	fun getList(@PathVariable installationId: Int): List<SectionListDTO> {
		return installationSectionService.getSections(installationId)
	}

	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/windows")
	fun getSlots(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<SectionSlotListDTO> {
		return installationSectionService.getWindows(installationId, sectionIndex)
	}

	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/windows/upcoming")
	fun getUpcomingSlots(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<SectionSlotOccurrenceDTO> {
		return installationSectionService.getUpcomingWindows(installationId, sectionIndex)
	}
}

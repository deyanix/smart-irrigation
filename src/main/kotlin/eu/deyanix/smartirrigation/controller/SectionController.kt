package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.service.SectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Section")
@RestController
class SectionController(
	private val sectionService: SectionService,
) {
	@GetMapping("/installations/any/sections/{sectionId}")
	fun getSections(@PathVariable sectionId: Int): SectionDTO =
		sectionService.getSection(sectionId)

	@GetMapping("/installations/{installationId}/sections")
	fun getSection(@PathVariable installationId: Int): List<SectionDTO> =
		sectionService.getSections(installationId)
			.toList()

	@PostMapping("/installations/any/sections/{sectionId}/stop")
	fun stop(@PathVariable sectionId: Int) =
		sectionService.stop(sectionId)
}

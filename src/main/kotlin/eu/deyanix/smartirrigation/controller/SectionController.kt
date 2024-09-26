package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.service.SectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Section")
@RestController
class SectionController(
	private val sectionService: SectionService,
) {
	@GetMapping("/installations/{installationId}/sections")
	fun getList(@PathVariable installationId: Int): List<SectionDTO> =
		sectionService.getSections(installationId)
			.toList()

	@GetMapping("/installations/any/sections/{sectionId}/irrigations")
	fun getIrrigations(@PathVariable sectionId: Int): List<IrrigationDTO> =
		sectionService.getIrrigations(sectionId)
			.toList()
}

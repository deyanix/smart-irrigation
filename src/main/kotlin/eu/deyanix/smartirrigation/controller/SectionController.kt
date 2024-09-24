package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SectionDTO
import eu.deyanix.smartirrigation.service.SectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class SectionController(
	private val sectionService: SectionService,
) {
	@GetMapping("/installations/{installationId}/sections")
	fun getList(@PathVariable installationId: Int): List<SectionDTO> =
		sectionService.getSections(installationId)
			.toList()

	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/irrigations")
	fun getIrrigations(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<IrrigationDTO> =
		sectionService.getIrrigations(installationId, sectionIndex)
			.toList()
}

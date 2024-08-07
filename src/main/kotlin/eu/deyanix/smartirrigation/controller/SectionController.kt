package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionListDTO
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
		return installationSectionService.getList(installationId)
	}
}

package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.InstallationSectionListDTO
import eu.deyanix.smartirrigation.service.InstallationSectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class InstallationSectionController(
	private val installationSectionService: InstallationSectionService,
) {
	@GetMapping("/installations/{installationId}/sections")
	fun getList(@PathVariable installationId: Int): List<InstallationSectionListDTO> {
		return installationSectionService.getList(installationId)
	}
}

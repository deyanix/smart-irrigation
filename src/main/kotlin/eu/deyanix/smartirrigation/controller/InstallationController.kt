package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.InstallationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class InstallationController(
	private val installationService: InstallationService
) {
	@GetMapping("/installations")
	fun getInstallations() =
		installationService.getInstallations()
}


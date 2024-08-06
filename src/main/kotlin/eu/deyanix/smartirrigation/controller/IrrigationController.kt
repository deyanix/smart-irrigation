package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.IrrigationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class IrrigationController(
	private val irrigationService: IrrigationService,
) {
	@PostMapping("/installations/{installationId}/irrigations/refresh")
	fun refresh(@PathVariable installationId: Int) {
		irrigationService.refresh(installationId)
	}
}

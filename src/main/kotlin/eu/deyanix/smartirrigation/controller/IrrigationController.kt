package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.service.IrrigationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Section Irrigation")
@RestController
class IrrigationController(
	private val irrigationService: IrrigationService,
) {
	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/irrigations/upcoming")
	fun getUpcomingIrrigations(@PathVariable installationId: Int, @PathVariable sectionIndex: Int) =
		irrigationService.getUpcomingIrrigations(installationId, sectionIndex).toList()

	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/irrigations/current")
	fun getCurrentIrrigations(@PathVariable installationId: Int, @PathVariable sectionIndex: Int) =
		irrigationService.getCurrentIrrigations(installationId, sectionIndex).toList()
}

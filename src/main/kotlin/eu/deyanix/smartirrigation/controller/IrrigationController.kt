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
	@GetMapping("/installations/any/sections/{sectionId}/irrigations/upcoming")
	fun getUpcomingIrrigations(@PathVariable sectionId: Int) =
		irrigationService.getUpcomingIrrigations(sectionId).toList()

	@GetMapping("/installations/any/sections/{sectionId}/irrigations/current")
	fun getCurrentIrrigations(@PathVariable sectionId: Int) =
		irrigationService.getCurrentIrrigations(sectionId).toList()
}

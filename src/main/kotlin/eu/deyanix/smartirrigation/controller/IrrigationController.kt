package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.IrrigationCriteria
import eu.deyanix.smartirrigation.dto.IrrigationDTO
import eu.deyanix.smartirrigation.dto.SearchResponse
import eu.deyanix.smartirrigation.service.IrrigationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Section Irrigation")
@RestController
class IrrigationController(
	private val irrigationService: IrrigationService,
) {
	@GetMapping("/installations/any/sections/{sectionId}/irrigations")
	fun getIrrigations(@PathVariable sectionId: Int, @ParameterObject criteria: IrrigationCriteria): SearchResponse<IrrigationDTO> =
		irrigationService.search(sectionId, criteria)

	@GetMapping("/installations/any/sections/{sectionId}/irrigations/upcoming")
	fun getUpcomingIrrigations(@PathVariable sectionId: Int) =
		irrigationService.getUpcomingIrrigations(sectionId)
}

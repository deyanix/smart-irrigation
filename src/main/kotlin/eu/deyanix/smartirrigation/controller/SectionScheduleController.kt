package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionScheduleCriteria
import eu.deyanix.smartirrigation.dto.SectionScheduleRequest
import eu.deyanix.smartirrigation.service.SectionScheduleService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "Section Schedule")
@RestController
class SectionScheduleController(
	private val sectionScheduleService: SectionScheduleService,
) {
	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/schedules")
	fun search(@PathVariable installationId: Int, @PathVariable sectionIndex: Int, @ParameterObject criteria: SectionScheduleCriteria) =
		sectionScheduleService.search(installationId, sectionIndex, criteria)

	@PostMapping("/installations/{installationId}/sections/{sectionIndex}/schedules")
	fun create(@PathVariable installationId: Int, @PathVariable sectionIndex: Int, @RequestBody data: SectionScheduleRequest) =
		sectionScheduleService.create(installationId, sectionIndex, data)

	@DeleteMapping("/installations/any/sections/any/schedules/{scheduleId}")
	fun delete(@PathVariable scheduleId: Int) =
		sectionScheduleService.delete(scheduleId)
}

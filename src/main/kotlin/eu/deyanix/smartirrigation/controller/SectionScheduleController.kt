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
	@GetMapping("/installations/any/sections/{sectionId}/schedules")
	fun search(@PathVariable sectionId: Int, @ParameterObject criteria: SectionScheduleCriteria) =
		sectionScheduleService.search(sectionId, criteria)

	@PostMapping("/installations/any/sections/{sectionId}/schedules")
	fun create(@PathVariable sectionId: Int, @RequestBody data: SectionScheduleRequest) =
		sectionScheduleService.create(sectionId, data)

	@DeleteMapping("/installations/any/sections/any/schedules/{scheduleId}")
	fun delete(@PathVariable scheduleId: Int) =
		sectionScheduleService.delete(scheduleId)
}

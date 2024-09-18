package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionScheduleRequest
import eu.deyanix.smartirrigation.service.SectionScheduleService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class SectionScheduleController(
	private val sectionScheduleService: SectionScheduleService,
) {
	@PostMapping("/installations/{installationId}/sections/{sectionIndex}/schedules")
	fun createSchedule(@PathVariable installationId: Int, @PathVariable sectionIndex: Int, @RequestBody data: SectionScheduleRequest) =
		sectionScheduleService.create(installationId, sectionIndex, data)

	@DeleteMapping("/installations/any/sections/any/schedules/{scheduleId}")
	fun deleteSchedule(@PathVariable scheduleId: Int) =
		sectionScheduleService.delete(scheduleId)
}

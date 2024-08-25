package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.*
import eu.deyanix.smartirrigation.service.SectionScheduleService
import eu.deyanix.smartirrigation.service.SectionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Installation")
@RestController
class SectionController(
	private val sectionService: SectionService,
	private val sectionScheduleService: SectionScheduleService,
) {
	@GetMapping("/installations/{installationId}/sections")
	fun getList(@PathVariable installationId: Int): List<SectionDTO> =
		sectionService.getSections(installationId)
			.toList()


	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/windows")
	fun getSlots(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<SectionSlotListDTO> =
		sectionService.getSlots(installationId, sectionIndex)
			.toList()


	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/windows/upcoming")
	fun getUpcomingSlots(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<SectionSlotOccurrenceDTO> =
		sectionService.getUpcomingSlots(installationId, sectionIndex)
			.toList()


	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/windows/current")
	fun getCurrentSlots(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<SectionSlotOccurrenceDTO> =
		sectionService.getCurrentSlots(installationId, sectionIndex)
			.toList()

	@PostMapping("/installations/{installationId}/sections/{sectionIndex}/start")
	fun start(@PathVariable installationId: Int, @PathVariable sectionIndex: Int) =
		sectionService.start(installationId, sectionIndex)

	@PostMapping("/installations/{installationId}/sections/{sectionIndex}/stop")
	fun stop(@PathVariable installationId: Int, @PathVariable sectionIndex: Int) =
		sectionService.stop(installationId, sectionIndex)

	@PostMapping("/installations/{installationId}/sections/{sectionIndex}/schedules")
	fun createSchedule(@PathVariable installationId: Int, @PathVariable sectionIndex: Int, @RequestBody data: SectionScheduleRequest) =
		sectionScheduleService.create(installationId, sectionIndex, data)

	@DeleteMapping("/installations/any/sections/any/schedules/{scheduleId}")
	fun deleteSchedule(@PathVariable scheduleId: Int) =
		sectionScheduleService.delete(scheduleId)

	@GetMapping("/installations/{installationId}/sections/{sectionIndex}/irrigations")
	fun getIrrigations(@PathVariable installationId: Int, @PathVariable sectionIndex: Int): List<IrrigationDTO> =
		sectionService.getIrrigations(installationId, sectionIndex)
			.toList()
}

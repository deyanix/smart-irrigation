package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionSlotRequest
import eu.deyanix.smartirrigation.service.SectionSlotService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Section Slot")
@RestController
class SectionSlotController(
	private val sectionSlotService: SectionSlotService,
) {
	@GetMapping("/installations/any/sections/{sectionId}/slots")
	fun getList(@PathVariable sectionId: Int) =
		sectionSlotService.getList(sectionId)

	@PostMapping("/installations/any/sections/{sectionId}/slots")
	fun create(@PathVariable sectionId: Int, @RequestBody data: SectionSlotRequest) =
		sectionSlotService.create(sectionId, data)

	@PutMapping("/installations/any/sections/any/slots/{slotId}")
	fun update(@PathVariable slotId: Int, @RequestBody data: SectionSlotRequest) =
		sectionSlotService.update(slotId, data)

	@DeleteMapping("/installations/any/sections/any/slots/{slotId}")
	fun delete(@PathVariable slotId: Int) =
		sectionSlotService.delete(slotId)
}

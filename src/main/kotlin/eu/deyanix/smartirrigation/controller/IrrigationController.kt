package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SectionSummaryDTO
import eu.deyanix.smartirrigation.service.IrrigationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@Tag(name = "Installation")
@RestController
class IrrigationController(
	private val irrigationService: IrrigationService,
) {
	@PostMapping("/installations/all/irrigations/refresh")
	fun refreshAll() {
		irrigationService.refreshAll()
	}

	@GetMapping("/installations/{installationId}/irrigations/summary")
	fun summarize(@PathVariable installationId: Int, @RequestParam from: Optional<LocalDate>, @RequestParam to: Optional<LocalDate>): Iterable<SectionSummaryDTO> {
		return irrigationService.summarize(installationId, from.orElse(LocalDate.now()), to.orElse(LocalDate.now()))
	}
}

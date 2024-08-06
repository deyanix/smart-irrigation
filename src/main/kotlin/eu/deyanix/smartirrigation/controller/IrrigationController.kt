package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.InstallationSectionSummaryDTO
import eu.deyanix.smartirrigation.service.IrrigationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@Tag(name = "Installation")
@RestController
class IrrigationController(
	private val irrigationService: IrrigationService,
) {
	@PostMapping("/installations/{installationId}/irrigations/refresh")
	fun refresh(@PathVariable installationId: Int) {
		irrigationService.refresh(installationId)
	}

	@GetMapping("/installations/{installationId}/irrigations/summary")
	fun summarize(@PathVariable installationId: Int, @RequestParam from: Optional<LocalDate>, @RequestParam to: Optional<LocalDate>): Iterable<InstallationSectionSummaryDTO> {
		return irrigationService.summarize(installationId, from.orElse(LocalDate.now()), to.orElse(LocalDate.now()))
	}
}

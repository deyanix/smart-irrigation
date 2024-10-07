package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.SensorResponse
import eu.deyanix.smartirrigation.service.SensorService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Sensor")
@RestController
class SensorController(private val sensorService: SensorService) {
	@GetMapping("/installations/{installationId}/sensors")
	fun getSensors(@PathVariable installationId: Int): List<SensorResponse> =
		sensorService.getSensors(installationId)
}

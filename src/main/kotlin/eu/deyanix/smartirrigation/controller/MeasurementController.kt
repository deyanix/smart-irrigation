package eu.deyanix.smartirrigation.controller

import eu.deyanix.smartirrigation.dto.MeasurementCriteria
import eu.deyanix.smartirrigation.dto.MeasurementItem
import eu.deyanix.smartirrigation.service.MeasurementService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Measurement")
@RestController
class MeasurementController(private val measurementService: MeasurementService) {
	@GetMapping("/sensors/any/items/{sensorItemId}/measurements")
	fun getInstallationIrrigations(@PathVariable sensorItemId: Int, @ParameterObject criteria: MeasurementCriteria): List<MeasurementItem> =
		measurementService.getMeasurements(sensorItemId, criteria)

}

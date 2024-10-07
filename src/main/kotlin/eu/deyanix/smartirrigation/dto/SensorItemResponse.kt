package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

class SensorItemResponse(
	var id: Int,
	var name: String,
	var measurementUnitId: Int?,
	var measurementUnitName: String,
	var measurementUnitSymbol: String,
	var lastMeasurementDate: OffsetDateTime?,
	var lastMeasurementValue: Double?,
)


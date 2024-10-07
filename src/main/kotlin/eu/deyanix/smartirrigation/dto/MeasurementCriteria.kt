package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

data class MeasurementCriteria(
	var dateFrom: OffsetDateTime?,
	var dateTo: OffsetDateTime?,
)

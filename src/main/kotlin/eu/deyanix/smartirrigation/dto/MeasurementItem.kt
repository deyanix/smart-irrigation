package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

data class MeasurementItem(
	val date: OffsetDateTime,
	val value: Double,
)

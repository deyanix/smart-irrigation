package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

data class IrrigationCriteria(
	val from: OffsetDateTime? = null,
	val to: OffsetDateTime? = null,
	val page: Int = 0,
	val pageSize: Int = 10,
)

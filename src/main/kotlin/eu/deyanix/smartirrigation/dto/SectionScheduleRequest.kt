package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

data class SectionScheduleRequest(
	val start: OffsetDateTime,
	val end: OffsetDateTime,
	val state: Boolean,
)

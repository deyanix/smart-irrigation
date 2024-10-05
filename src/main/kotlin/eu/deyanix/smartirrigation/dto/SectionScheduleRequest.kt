package eu.deyanix.smartirrigation.dto

import java.time.ZonedDateTime

data class SectionScheduleRequest(
	val start: ZonedDateTime,
	val end: ZonedDateTime,
	val state: Boolean,
)

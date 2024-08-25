package eu.deyanix.smartirrigation.dto

import java.time.LocalDateTime

data class SectionScheduleRequest(
	val start: LocalDateTime,
	val end: LocalDateTime,
	val state: Boolean,
)

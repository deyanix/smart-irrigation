package eu.deyanix.smartirrigation.dto

import java.time.LocalDateTime

data class UpcomingIrrigationDTO(
	val start: LocalDateTime,
	val durationMinutes: Int,
)

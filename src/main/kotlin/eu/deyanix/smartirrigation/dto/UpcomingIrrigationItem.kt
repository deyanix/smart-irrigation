package eu.deyanix.smartirrigation.dto

import java.time.LocalDateTime

data class UpcomingIrrigationItem(
	val start: LocalDateTime,
	val end: LocalDateTime,
	val sectionId: Int,
	val sectionName: String,
	val sources: List<Source>
) {
	data class Source(
		val start: LocalDateTime,
		val end: LocalDateTime,
		val slotId: Int? = null,
		val scheduleId: Int? = null,
		val state: Boolean = false,
	)
}



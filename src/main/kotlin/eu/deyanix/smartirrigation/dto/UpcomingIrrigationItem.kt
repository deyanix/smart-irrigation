package eu.deyanix.smartirrigation.dto

import java.time.ZonedDateTime

data class UpcomingIrrigationItem(
	val start: ZonedDateTime,
	val end: ZonedDateTime,
	val sectionId: Int,
	val sectionName: String,
	val sources: List<Source>
) {
	data class Source(
		val start: ZonedDateTime,
		val end: ZonedDateTime,
		val slotId: Int? = null,
		val scheduleId: Int? = null,
		val state: Boolean = false,
	)
}



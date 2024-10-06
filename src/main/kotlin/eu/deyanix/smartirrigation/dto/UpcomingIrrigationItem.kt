package eu.deyanix.smartirrigation.dto

import java.time.OffsetDateTime

data class UpcomingIrrigationItem(
	val start: OffsetDateTime,
	val end: OffsetDateTime,
	val sectionId: Int,
	val sectionName: String,
	val sources: List<Source>
) {
	data class Source(
		val start: OffsetDateTime,
		val end: OffsetDateTime,
		val slotId: Int? = null,
		val scheduleId: Int? = null,
		val state: Boolean = false,
	)
}



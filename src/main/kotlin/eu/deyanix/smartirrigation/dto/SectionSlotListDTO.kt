package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.SectionSlot
import eu.deyanix.smartirrigation.dao.SectionSlotWeekday
import java.time.LocalTime

data class SectionSlotListDTO(
	val id: Int,
	val start: LocalTime,
	val end: LocalTime,
	val weekdays: List<Int>,
) {
	constructor(sectionSlot: SectionSlot) : this(
		id = sectionSlot.id,
		start = sectionSlot.start,
		end = sectionSlot.end,
		weekdays = sectionSlot.weekdays.map(SectionSlotWeekday::weekday)
	)
}

package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.SectionSlot
import eu.deyanix.smartirrigation.dao.SectionSlotWeekday
import java.time.OffsetTime

data class SectionSlotRequest(
	val start: OffsetTime,
	val end: OffsetTime,
	val weekdays: Array<Int>,
) {
	fun applyTo(slot: SectionSlot) {
		slot.start = start
		slot.end = end
		slot.weekdays.clear()
		slot.weekdays.addAll(
			weekdays.map { SectionSlotWeekday(sectionSlot = slot, weekday = it) })
	}
}

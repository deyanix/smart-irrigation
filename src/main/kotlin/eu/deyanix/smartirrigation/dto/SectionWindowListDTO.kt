package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.SectionWindow
import eu.deyanix.smartirrigation.dao.SectionWindowWeekday
import java.time.LocalTime

data class SectionWindowListDTO(
	val id: Int,
	val start: LocalTime,
	val end: LocalTime,
	val weekdays: List<Int>,
) {
	constructor(sectionWindow: SectionWindow) : this(
		id = sectionWindow.id,
		start = sectionWindow.start,
		end = sectionWindow.end,
		weekdays = sectionWindow.weekdays.map(SectionWindowWeekday::weekday)
	)
}

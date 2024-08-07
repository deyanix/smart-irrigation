package eu.deyanix.smartirrigation.dto

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters


data class SectionWindowOccurrenceDTO(
	val start: LocalDateTime,
	val end: LocalDateTime,
) {
	class Builder(
		val weekday: DayOfWeek,
		val start: LocalTime,
		val end: LocalTime,
	) {
		val startWeekday = weekday
		val endWeekday = weekday.plus(if (start < end) 0 else 1)

		fun next(ref: LocalDateTime): SectionWindowOccurrenceDTO {
			if (contains(ref)) {
				return SectionWindowOccurrenceDTO(
					start = ref.with(startWeekday).with(start),
					end = ref.with(endWeekday).with(end),
				)
			}

			if (isThenEnded(ref)) {
				return SectionWindowOccurrenceDTO(
					start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
					end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
				)
			}

			return SectionWindowOccurrenceDTO(
				start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
				end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
			)
		}

		private fun contains(ref: LocalDateTime): Boolean {
			val refTime = ref.toLocalTime()
			val refWeekday = ref.dayOfWeek
			return if (weekday == endWeekday) {
				refWeekday == weekday && refTime in start..end
			} else {
				(refWeekday == startWeekday && refTime >= start) || (refWeekday == endWeekday && refTime <= end)
			}
		}

		private fun isThenEnded(ref: LocalDateTime): Boolean {
			return endWeekday == ref.dayOfWeek && ref.toLocalTime() > end
		}
	}
}

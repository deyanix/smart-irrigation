package eu.deyanix.smartirrigation.dto

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters


data class SectionSlotOccurrenceDTO(
	val start: LocalDateTime,
	val end: LocalDateTime,
) {
	class Builder(
		private val weekday: DayOfWeek,
		private val start: LocalTime,
		private val end: LocalTime,
	) {
		private val startWeekday = weekday
		private val endWeekday = weekday.plus(if (start < end) 0 else 1)

		fun next(ref: LocalDateTime): SectionSlotOccurrenceDTO {
			val previousStart = ref.with(TemporalAdjusters.previousOrSame(startWeekday)).with(start)
			val previousEnd = previousStart.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end)
			if (ref in previousStart..previousEnd) {
				return SectionSlotOccurrenceDTO(
					start = previousStart,
					end = previousEnd,
				)
			}

			if (isThenEnded(ref)) {
				return SectionSlotOccurrenceDTO(
					start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
					end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
				)
			}

			return SectionSlotOccurrenceDTO(
				start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
				end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
			)
		}

		private fun isThenEnded(ref: LocalDateTime): Boolean {
			return endWeekday == ref.dayOfWeek && ref.toLocalTime() > end
		}
	}
}

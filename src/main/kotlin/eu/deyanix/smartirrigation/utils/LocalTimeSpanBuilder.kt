package eu.deyanix.smartirrigation.utils

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

class LocalTimeSpanBuilder(
	private val ref: LocalDateTime,
	private val start: LocalTime,
	private val end: LocalTime,
) {
	fun next(startWeekday: DayOfWeek): LocalTimeSpan {
		val endWeekday = startWeekday.plus(if (start < end) 0 else 1)

		val previousStart = ref.with(TemporalAdjusters.previousOrSame(startWeekday)).with(start)
		val previousEnd = previousStart.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end)
		if (ref in previousStart..previousEnd) {
			return LocalTimeSpan(
				start = previousStart,
				end = previousEnd
			)
		}

		if (endWeekday == ref.dayOfWeek && ref.toLocalTime() > end) {
			return LocalTimeSpan(
				start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
				end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
			)
		}

		return LocalTimeSpan(
			start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
			end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
		)
	}
}

package eu.deyanix.smartirrigation.utils

import java.time.DayOfWeek
import java.time.OffsetTime
import java.time.OffsetDateTime
import java.time.temporal.TemporalAdjusters

class TimeSpanBuilder(
	private val ref: OffsetDateTime,
	private val start: OffsetTime,
	private val end: OffsetTime,
) {
	fun next(startWeekday: DayOfWeek): TimeSpan {
		val endWeekday = startWeekday.plus(if (start < end) 0 else 1)

		val previousStart = ref.with(TemporalAdjusters.previousOrSame(startWeekday)).with(start)
		val previousEnd = previousStart.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end)
		if (ref in previousStart..previousEnd) {
			return TimeSpan(
				start = previousStart,
				end = previousEnd
			)
		}

		if (endWeekday == ref.dayOfWeek && ref.toOffsetTime() > end) {
			return TimeSpan(
				start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
				end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
			)
		}

		return TimeSpan(
			start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
			end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
		)
	}
}

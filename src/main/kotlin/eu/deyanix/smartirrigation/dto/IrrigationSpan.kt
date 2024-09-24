package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters


data class IrrigationSpan(
	val span: LocalTimeSpan,
	val state: Boolean,
) {
	class Builder(
		private val start: LocalTime,
		private val end: LocalTime,
		private val state: Boolean,
	) {
		fun next(ref: LocalDateTime, startWeekday: DayOfWeek): IrrigationSpan {
			val endWeekday = startWeekday.plus(if (start < end) 0 else 1)

			val previousStart = ref.with(TemporalAdjusters.previousOrSame(startWeekday)).with(start)
			val previousEnd = previousStart.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end)
			if (ref in previousStart..previousEnd) {
				return IrrigationSpan(
					span = LocalTimeSpan(
						start = previousStart,
						end = previousEnd
					),
					state = state,
				)
			}

			if (endWeekday == ref.dayOfWeek && ref.toLocalTime() > end) {
				return IrrigationSpan(
					span = LocalTimeSpan(
						start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
						end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
					),
					state = state,
				)
			}

			return IrrigationSpan(
				span = LocalTimeSpan(
					start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
					end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
				),
				state = state,
			)
		}
	}
}

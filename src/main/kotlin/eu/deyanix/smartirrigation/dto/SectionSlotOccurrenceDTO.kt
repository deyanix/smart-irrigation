package eu.deyanix.smartirrigation.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters


data class SectionSlotOccurrenceDTO(
	val start: LocalDateTime,
	val end: LocalDateTime,
	val source: Source,
) {
	@JsonIgnore
	val span = LocalTimeSpan(start, end)

	constructor(span: LocalTimeSpan, source: Source): this(span.start, span.end, source)

	enum class Source {
		SLOT,
		SCHEDULE,
		SCHEDULE_SHUTDOWN,
	}

	class Builder(
		private val weekday: DayOfWeek,
		private val start: LocalTime,
		private val end: LocalTime,
		private val source: Source,
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
					source = source,
				)
			}

			if (isThenEnded(ref)) {
				return SectionSlotOccurrenceDTO(
					start = ref.with(TemporalAdjusters.next(startWeekday)).with(start),
					end = ref.with(TemporalAdjusters.next(endWeekday)).with(end),
					source = source,
				)
			}

			return SectionSlotOccurrenceDTO(
				start = ref.with(TemporalAdjusters.nextOrSame(startWeekday)).with(start),
				end = ref.with(TemporalAdjusters.nextOrSame(endWeekday)).with(end),
				source = source,
			)
		}

		private fun isThenEnded(ref: LocalDateTime): Boolean {
			return endWeekday == ref.dayOfWeek && ref.toLocalTime() > end
		}
	}
}

package eu.deyanix.smartirrigation.utils

import java.time.Duration
import java.time.LocalDateTime

class LocalTimeSpan(
	val start: LocalDateTime,
	val end: LocalDateTime
) {
	val duration = Duration.between(start, end)

	fun isBetween(date: LocalDateTime): Boolean {
		return date in start..end
	}

	fun isOverlap(other: LocalTimeSpan): Boolean {
		return isBetween(other.start) || isBetween(other.end)
	}

	fun intersection(range: LocalTimeSpan): LocalTimeSpan? {
		if (!isOverlap(range)) {
			return null
		}

		return LocalTimeSpan(
			start = LocalDateTimes.max(start, range.start),
			end = LocalDateTimes.min(end, range.end),
		)
	}

	fun union(range: LocalTimeSpan): Array<LocalTimeSpan> {
		if (!isOverlap(range)) {
			return arrayOf(range, this)
		}

		return arrayOf(LocalTimeSpan(
			start = LocalDateTimes.min(start, range.start),
			end = LocalDateTimes.max(end, range.end),
		))
	}
}

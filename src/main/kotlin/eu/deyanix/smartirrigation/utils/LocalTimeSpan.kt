package eu.deyanix.smartirrigation.utils

import java.time.LocalDateTime

class LocalTimeSpan(
	val start: LocalDateTime,
	val end: LocalDateTime
) {
	fun isBetween(date: LocalDateTime): Boolean {
		return date in start..end
	}

	fun isOverlap(other: LocalTimeSpan): Boolean {
		return isBetween(other.start) || isBetween(other.end)
	}

	fun isContains(other: LocalTimeSpan): Boolean {
		return isBetween(other.start) && isBetween(other.end)
	}

	fun isWithin(other: LocalTimeSpan): Boolean {
		return other.isContains(this)
	}

	fun exclude(other: LocalTimeSpan): Array<LocalTimeSpan> {
		if (isWithin(other)) {
			return arrayOf()
		}

		if (!isOverlap(other)) {
			return arrayOf(this)
		}

		val result = mutableListOf<LocalTimeSpan>()
		if (start < other.start)
			result.add(LocalTimeSpan(start, other.start))
		if (end > other.end)
			result.add(LocalTimeSpan(other.end, end))

		return result.toTypedArray()
	}

	fun intersection(other: LocalTimeSpan): LocalTimeSpan? {
		if (!isOverlap(other)) {
			return null
		}

		return LocalTimeSpan(
			start = LocalDateTimes.max(start, other.start),
			end = LocalDateTimes.min(end, other.end),
		)
	}

	fun sum(other: LocalTimeSpan): LocalTimeSpan? {
		if (!isOverlap(other)) {
			return null
		}

		return LocalTimeSpan(
			start = LocalDateTimes.min(start, other.start),
			end = LocalDateTimes.max(end, other.end),
		)
	}

	fun union(other: LocalTimeSpan): Array<LocalTimeSpan> {
		val result = sum(other)

		if (result != null) {
			return arrayOf(result)
		}
		return arrayOf(other, this)
	}
}

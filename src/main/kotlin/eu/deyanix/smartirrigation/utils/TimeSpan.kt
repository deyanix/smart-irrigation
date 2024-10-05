package eu.deyanix.smartirrigation.utils

import java.time.ZonedDateTime

class TimeSpan(
	val start: ZonedDateTime,
	val end: ZonedDateTime
) {
	fun isBetween(date: ZonedDateTime): Boolean {
		return date in start..end
	}

	fun isOverlap(other: TimeSpan): Boolean {
		return start <= other.end && end >= other.start
	}

	fun isContains(other: TimeSpan): Boolean {
		return isBetween(other.start) && isBetween(other.end)
	}

	fun isWithin(other: TimeSpan): Boolean {
		return other.isContains(this)
	}

	fun exclude(other: TimeSpan): Array<TimeSpan> {
		if (isWithin(other)) {
			return arrayOf()
		}

		if (!isOverlap(other)) {
			return arrayOf(this)
		}

		val result = mutableListOf<TimeSpan>()
		if (start < other.start)
			result.add(TimeSpan(start, other.start))
		if (end > other.end)
			result.add(TimeSpan(other.end, end))

		return result.toTypedArray()
	}

	fun intersection(other: TimeSpan): TimeSpan? {
		if (!isOverlap(other)) {
			return null
		}

		return TimeSpan(
			start = ZonedDateTimes.max(start, other.start),
			end = ZonedDateTimes.min(end, other.end),
		)
	}

	fun sum(other: TimeSpan): TimeSpan? {
		if (!isOverlap(other)) {
			return null
		}

		return TimeSpan(
			start = ZonedDateTimes.min(start, other.start),
			end = ZonedDateTimes.max(end, other.end),
		)
	}

	fun union(other: TimeSpan): Array<TimeSpan> {
		val result = sum(other)

		if (result != null) {
			return arrayOf(result)
		}
		return arrayOf(other, this)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as TimeSpan

		if (start != other.start) return false
		if (end != other.end) return false

		return true
	}

	override fun hashCode(): Int {
		var result = start.hashCode()
		result = 31 * result + end.hashCode()
		return result
	}

	override fun toString(): String {
		return "LocalTimeSpan(start=$start, end=$end)"
	}
}

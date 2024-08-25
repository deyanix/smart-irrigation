package eu.deyanix.smartirrigation.utils

import java.time.LocalDateTime

internal object LocalDateTimes {
	fun min(dateTime: LocalDateTime, other: LocalDateTime): LocalDateTime {
		return if (dateTime < other) dateTime else other
	}

	fun max(dateTime: LocalDateTime, other: LocalDateTime): LocalDateTime {
		return if (dateTime > other) dateTime else other
	}

	fun minmax(dateTime: LocalDateTime, from: LocalDateTime, to: LocalDateTime): LocalDateTime {
		if (dateTime > to) {
			return to
		} else if (dateTime < from) {
			return from
		}
		return dateTime
	}
}


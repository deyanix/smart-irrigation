package eu.deyanix.smartirrigation.utils

import java.time.ZonedDateTime

internal object ZonedDateTimes {
	fun min(dateTime: ZonedDateTime, other: ZonedDateTime): ZonedDateTime {
		return if (dateTime < other) dateTime else other
	}

	fun max(dateTime: ZonedDateTime, other: ZonedDateTime): ZonedDateTime {
		return if (dateTime > other) dateTime else other
	}

	fun minmax(dateTime: ZonedDateTime, from: ZonedDateTime, to: ZonedDateTime): ZonedDateTime {
		if (dateTime > to) {
			return to
		} else if (dateTime < from) {
			return from
		}
		return dateTime
	}
}


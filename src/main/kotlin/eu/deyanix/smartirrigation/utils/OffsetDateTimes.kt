package eu.deyanix.smartirrigation.utils

import java.time.OffsetDateTime

internal object OffsetDateTimes {
	fun min(dateTime: OffsetDateTime, other: OffsetDateTime): OffsetDateTime {
		return if (dateTime < other) dateTime else other
	}

	fun max(dateTime: OffsetDateTime, other: OffsetDateTime): OffsetDateTime {
		return if (dateTime > other) dateTime else other
	}

	fun minmax(dateTime: OffsetDateTime, from: OffsetDateTime, to: OffsetDateTime): OffsetDateTime {
		if (dateTime > to) {
			return to
		} else if (dateTime < from) {
			return from
		}
		return dateTime
	}
}


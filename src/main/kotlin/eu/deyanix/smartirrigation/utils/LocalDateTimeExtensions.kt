package eu.deyanix.smartirrigation.utils

import java.time.LocalDateTime

internal object LocalDateTimeExtensions {
	fun LocalDateTime.minmax(from: LocalDateTime, to: LocalDateTime): LocalDateTime {
		if (isAfter(to)) {
			return to
		} else if (isBefore(from)) {
			return from
		}
		return this
	}
}


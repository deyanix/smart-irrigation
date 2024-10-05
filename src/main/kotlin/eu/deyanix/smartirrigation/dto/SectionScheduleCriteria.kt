package eu.deyanix.smartirrigation.dto

import java.time.ZonedDateTime

data class SectionScheduleCriteria(
	val from: ZonedDateTime? = null,
	val to: ZonedDateTime? = null,
	val page: Int = 0,
	val pageSize: Int = 10,
)

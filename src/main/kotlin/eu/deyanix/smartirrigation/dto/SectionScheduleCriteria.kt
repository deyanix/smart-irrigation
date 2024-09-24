package eu.deyanix.smartirrigation.dto

import java.time.LocalDateTime

data class SectionScheduleCriteria(
	val from: LocalDateTime? = null,
	val to: LocalDateTime? = null,
	val page: Int = 0,
	val pageSize: Int = 10,
)

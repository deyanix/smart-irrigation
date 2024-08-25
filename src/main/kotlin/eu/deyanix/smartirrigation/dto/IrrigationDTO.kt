package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Irrigation
import java.time.LocalDateTime

data class IrrigationDTO(
	val id: Int,
	val start: LocalDateTime,
	val end: LocalDateTime,
	val finished: Boolean,
) {
	constructor(irrigation: Irrigation) : this(
		id = irrigation.id!!,
		start = irrigation.start,
		end = irrigation.end,
		finished = irrigation.finished,
	)
}

package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Irrigation
import java.time.OffsetDateTime

data class IrrigationDTO(
	val id: Int,
	val sectionId: Int,
	val sectionName: String,
	val start: OffsetDateTime,
	val end: OffsetDateTime,
	val finished: Boolean,
) {
	constructor(irrigation: Irrigation) : this(
		id = irrigation.id!!,
		sectionId = irrigation.section.id,
		sectionName = irrigation.section.name,
		start = irrigation.start,
		end = irrigation.end,
		finished = irrigation.finished,
	)
}

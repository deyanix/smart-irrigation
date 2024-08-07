package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Section

data class SectionListDTO(
	val id: Int,
	val name: String,
) {
	constructor(section: Section) : this(
		id = section.id,
		name = section.name,
	)
}

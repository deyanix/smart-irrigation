package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Section

data class SectionDTO(
	val id: Int,
	val name: String,
	val irrigating: Boolean,
) {
	constructor(section: Section, irrigating: Boolean) : this(
		id = section.id,
		name = section.name,
		irrigating = irrigating,
	)
}

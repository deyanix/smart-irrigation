package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.InstallationSection

class InstallationSectionListDTO(
	val id: Int,
	val name: String,
	val installationId: Int,
	val installationName: String,
) {
	constructor(installationSection: InstallationSection) : this(
		id = installationSection.id,
		name = installationSection.name,
		installationId = installationSection.installation.id,
		installationName = installationSection.installation.name
	)
}

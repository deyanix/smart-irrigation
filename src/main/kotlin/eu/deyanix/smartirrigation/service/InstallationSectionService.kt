package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.InstallationSectionListDTO
import eu.deyanix.smartirrigation.repository.InstallationSectionRepository
import org.springframework.stereotype.Service

@Service
class InstallationSectionService(
	val repository: InstallationSectionRepository
) {
	fun getList(installationId: Int): List<InstallationSectionListDTO> =
		repository.findAllByInstallation(installationId)
			.map(::InstallationSectionListDTO)
			.toList()
}

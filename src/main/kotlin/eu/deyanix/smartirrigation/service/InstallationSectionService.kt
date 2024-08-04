package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.InstallationSectionListDTO
import eu.deyanix.smartirrigation.repository.InstallationSectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InstallationSectionService(
	val repository: InstallationSectionRepository
) {
	@Transactional(readOnly = true)
	fun getList(installationId: Int): List<InstallationSectionListDTO> =
		repository.findAllByInstallation(installationId)
			.map(::InstallationSectionListDTO)
			.toList()
}

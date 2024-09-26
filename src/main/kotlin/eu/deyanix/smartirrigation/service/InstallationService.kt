package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.repository.InstallationRepository
import org.springframework.stereotype.Service

@Service
class InstallationService(
	val installationRepository: InstallationRepository
) {
	fun getInstallations() =
		installationRepository.findAll()
}

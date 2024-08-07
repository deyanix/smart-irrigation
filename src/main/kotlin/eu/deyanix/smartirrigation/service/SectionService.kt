package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dto.SectionListDTO
import eu.deyanix.smartirrigation.repository.SectionRepository
import org.springframework.stereotype.Service

@Service
class SectionService(
	private val repository: SectionRepository
) {
	fun getList(installationId: Int): List<SectionListDTO> =
		repository.findAllByInstallation(installationId)
			.map(::SectionListDTO)
			.toList()
}

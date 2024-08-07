package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SectionRepository : JpaRepository<Section, Int> {
	@Query("SELECT S FROM Section S WHERE S.installation.id = :installationId ORDER BY S.index")
	fun findAllByInstallation(installationId: Int): Iterable<Section>
}

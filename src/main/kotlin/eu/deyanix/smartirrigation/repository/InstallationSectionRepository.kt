package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.InstallationSection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InstallationSectionRepository : JpaRepository<InstallationSection, Int> {
	@Query("SELECT S FROM InstallationSection S WHERE S.installation.id = :installationId ORDER BY S.installationIndex")
	fun findAllByInstallation(installationId: Int): Iterable<InstallationSection>
}

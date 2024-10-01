package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Installation
import eu.deyanix.smartirrigation.dao.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.stream.Stream

interface SectionRepository : JpaRepository<Section, Int> {
	@Query("SELECT S FROM Section S WHERE S.installation = :installation ORDER BY S.index")
	fun findAllByInstallation(installation: Installation): Stream<Section>
}

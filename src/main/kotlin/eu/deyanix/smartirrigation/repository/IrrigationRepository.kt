package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Irrigation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IrrigationRepository : JpaRepository<Irrigation, Int> {
	@Query("SELECT I FROM Irrigation I WHERE I.installationSection.installation.id = :installationId AND I.finished = false")
	fun findAllUnfinished(installationId: Int): Iterable<Irrigation>
}

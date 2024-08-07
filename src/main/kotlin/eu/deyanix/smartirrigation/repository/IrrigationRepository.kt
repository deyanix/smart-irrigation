package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Irrigation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.stream.Stream

interface IrrigationRepository : JpaRepository<Irrigation, Int> {
	@Query("SELECT I FROM Irrigation I WHERE I.section.installation.id = :installationId AND I.finished = false")
	fun findAllUnfinished(installationId: Int): Iterable<Irrigation>

	@Query("SELECT I " +
			"FROM Irrigation I " +
			"WHERE " +
			"	I.section.installation.id = :installationId AND " +
			"	(" +
			"		(I.start >= :from AND I.start <= :to) OR" +
			"		(I.end >= :from AND I.end <= :to)" +
			"	)")
	fun findAllBetween(installationId: Int, from: LocalDateTime, to: LocalDateTime): Stream<Irrigation>
}

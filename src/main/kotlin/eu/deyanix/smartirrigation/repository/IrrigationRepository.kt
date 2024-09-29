package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Installation
import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dao.Section
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.stream.Stream

interface IrrigationRepository : JpaRepository<Irrigation, Int> {
	@Query("SELECT I FROM Irrigation I WHERE I.section = :section AND I.finished = false")
	fun findAllUnfinishedBySection(section: Section): Stream<Irrigation>

	@Query("SELECT I FROM Irrigation I " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR I.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR I.start <= :dateTo) " +
			"AND I.section = :section " +
			"ORDER BY I.start DESC")
	fun findPageBySectionBetween(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, pageable: Pageable): Page<Irrigation>

	@Query("SELECT I FROM Irrigation I " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR I.start >= :dateFrom OR I.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR I.start <= :dateTo OR I.end <= :dateTo) " +
			"AND I.section.installation = :installation " +
			"ORDER BY I.start DESC")
	fun findPageByInstallationBetween(installation: Installation, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, pageable: Pageable): Page<Irrigation>
}

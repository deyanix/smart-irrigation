package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.stream.Stream

interface SectionScheduleRepository : JpaRepository<SectionSchedule, Int> {
	@Query("SELECT SS FROM SectionSchedule SS " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR SS.start >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR SS.end <= :dateTo) " +
			"AND SS.section.installation.id = :installationId " +
			"AND SS.section.index = :sectionIndex ")
	fun findAllByCriteria(installationId: Int, sectionIndex: Int, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, pageable: Pageable): Page<SectionSchedule>

	@Query("SELECT SS FROM SectionSchedule SS WHERE SS.section = :section AND (SS.start < :until OR SS.end < :until)")
	fun findAllBySectionUntil(section: Section, until: LocalDateTime): Stream<SectionSchedule>
}

package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime
import java.util.stream.Stream

interface SectionScheduleRepository : JpaRepository<SectionSchedule, Int> {
	@Query("SELECT SS FROM SectionSchedule SS " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR SS.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR SS.start <= :dateTo) " +
			"AND SS.section = :section " +
			"ORDER BY SS.start")
	fun findPageBySectionBetween(section: Section, dateFrom: OffsetDateTime?, dateTo: OffsetDateTime?, pageable: Pageable): Page<SectionSchedule>

	@Query("SELECT SS FROM SectionSchedule SS " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR SS.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR SS.start <= :dateTo) " +
			"AND (:state IS NULL OR SS.state = :state) " +
			"AND SS.section = :section")
	fun findAllBySectionInTime(section: Section, dateFrom: OffsetDateTime?, dateTo: OffsetDateTime?, state: Boolean?): Stream<SectionSchedule>
}

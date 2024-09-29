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
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR SS.start >= :dateFrom OR SS.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR SS.start <= :dateTo OR SS.end <= :dateTo) " +
			"AND SS.section = :section " +
			"ORDER BY SS.start")
	fun findPageBySectionBetween(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, pageable: Pageable): Page<SectionSchedule>

	@Query("SELECT SS FROM SectionSchedule SS " +
			"WHERE (CAST(:dateFrom AS TIMESTAMP) IS NULL OR SS.start >= :dateFrom OR SS.end >= :dateFrom) " +
			"AND (CAST(:dateTo AS TIMESTAMP) IS NULL OR SS.start <= :dateTo OR SS.end <= :dateTo) " +
			"AND (:state IS NULL OR SS.state = :state) " +
			"AND SS.section = :section")
	fun findAllBySectionBetween(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?): Stream<SectionSchedule>

	@Query("SELECT SS FROM SectionSchedule SS " +
			"WHERE (" +
			"	(CAST(:dateFrom AS TIMESTAMP) IS NULL OR (SS.start <= :dateFrom AND SS.end >= :dateFrom)) " +
			"	OR (CAST(:dateTo AS TIMESTAMP) IS NULL OR (SS.start <= :dateTo AND SS.end >= :dateTo))" +
			") " +
			"AND (:state IS NULL OR SS.state = :state) " +
			"AND SS.section = :section")
	fun findAllBySectionInTime(section: Section, dateFrom: LocalDateTime?, dateTo: LocalDateTime?, state: Boolean?): Stream<SectionSchedule>
}

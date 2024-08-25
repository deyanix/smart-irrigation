package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.stream.Stream

interface SectionScheduleRepository : JpaRepository<SectionSchedule, Int> {
	@Query("SELECT SS FROM SectionSchedule SS WHERE SS.section = :section AND (SS.start < :until OR SS.end < :until)")
	fun findAllBySectionUntil(section: Section, until: LocalDateTime): Stream<SectionSchedule>
}

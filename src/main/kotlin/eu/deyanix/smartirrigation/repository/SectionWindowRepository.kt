package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.SectionWindow
import eu.deyanix.smartirrigation.dao.SectionWindowWeekday
import eu.deyanix.smartirrigation.dao.SectionWindowWeekdayId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.stream.Stream

interface SectionWindowRepository: JpaRepository<SectionWindowWeekday, SectionWindowWeekdayId> {
	@Query("SELECT SW FROM SectionWindow SW WHERE SW.section.installation.id = :installationId AND SW.section.index = :sectionIndex")
	fun findAllBySection(installationId: Int, sectionIndex: Int): Stream<SectionWindow>
}

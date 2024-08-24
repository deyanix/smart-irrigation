package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.SectionSlot
import eu.deyanix.smartirrigation.dao.SectionSlotWeekday
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.stream.Stream

interface SectionSlotRepository: JpaRepository<SectionSlotWeekday, SectionSlotWeekday.SectionSlotWeekdayId> {
	@Query("SELECT SW FROM SectionSlot SW WHERE SW.section.installation.id = :installationId AND SW.section.index = :sectionIndex")
	fun findAllBySection(installationId: Int, sectionIndex: Int): Stream<SectionSlot>
}

package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSlot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.stream.Stream

interface SectionSlotRepository: JpaRepository<SectionSlot, Int> {
	@Query("SELECT SS FROM SectionSlot SS WHERE SS.section = :section")
	fun findAllBySection(section: Section): Stream<SectionSlot>
}

package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dao.SectionSlot
import java.time.LocalDateTime

interface IrrigationSource {
	val start: LocalDateTime
	val end: LocalDateTime
	val section: Section

	data class SectionScheduleSource(
		val schedule: SectionSchedule,
	) : IrrigationSource {
		override val start: LocalDateTime = schedule.start
		override val end: LocalDateTime = schedule.end
		override val section: Section = schedule.section
	}

	data class SectionSlotSource(
		val slot: SectionSlot,
		override val start: LocalDateTime,
		override val end: LocalDateTime,
	) : IrrigationSource {
		override val section: Section = slot.section
	}
}



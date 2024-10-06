package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dao.SectionSlot
import java.time.OffsetDateTime

interface IrrigationSource {
	val start: OffsetDateTime
	val end: OffsetDateTime
	val section: Section

	data class SectionScheduleSource(
		val schedule: SectionSchedule,
	) : IrrigationSource {
		override val start: OffsetDateTime = schedule.start
		override val end: OffsetDateTime = schedule.end
		override val section: Section = schedule.section
	}

	data class SectionSlotSource(
		val slot: SectionSlot,
		override val start: OffsetDateTime,
		override val end: OffsetDateTime,
	) : IrrigationSource {
		override val section: Section = slot.section
	}
}



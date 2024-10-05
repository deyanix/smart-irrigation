package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dao.SectionSlot
import java.time.ZonedDateTime

interface IrrigationSource {
	val start: ZonedDateTime
	val end: ZonedDateTime
	val section: Section

	data class SectionScheduleSource(
		val schedule: SectionSchedule,
	) : IrrigationSource {
		override val start: ZonedDateTime = schedule.start
		override val end: ZonedDateTime = schedule.end
		override val section: Section = schedule.section
	}

	data class SectionSlotSource(
		val slot: SectionSlot,
		override val start: ZonedDateTime,
		override val end: ZonedDateTime,
	) : IrrigationSource {
		override val section: Section = slot.section
	}
}



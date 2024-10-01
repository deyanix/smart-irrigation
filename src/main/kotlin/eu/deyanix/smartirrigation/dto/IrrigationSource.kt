package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dao.SectionSlot
import java.time.LocalDateTime

interface IrrigationSource {
	val start: LocalDateTime
	val end: LocalDateTime

	data class SectionScheduleSource(
		val schedule: SectionSchedule,
		override val start: LocalDateTime = schedule.start,
		override val end: LocalDateTime = schedule.end,
	) : IrrigationSource

	data class SectionSlotSource(
		val slot: SectionSlot,
		override val start: LocalDateTime,
		override val end: LocalDateTime,
	) : IrrigationSource
}



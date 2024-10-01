package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.utils.LocalTimeSpan


data class IrrigationSpan(
	val timeSpan: LocalTimeSpan,
	val state: Boolean,
	val sources: Array<IrrigationSource>,
) {
	fun merge(other: IrrigationSpan, timeSpan: LocalTimeSpan): IrrigationSpan =
		IrrigationSpan(
			timeSpan = timeSpan,
			state = state || other.state,
			sources = listOf(*sources, *other.sources)
				.filter { timeSpan.isOverlap(LocalTimeSpan(it.start, it.end)) }
				.distinct()
				.toTypedArray()
		)

	fun union(other: IrrigationSpan): List<IrrigationSpan> {
		if (state != other.state || !timeSpan.isOverlap(other.timeSpan)) {
			return listOf(this, other)
		}

		val resultTimeSpans = timeSpan.union(other.timeSpan)
		return resultTimeSpans
			.map { merge(other, it) }
			.toList()
	}

	fun exclude(other: IrrigationSpan): List<IrrigationSpan> {
		if (state == other.state) {
			return listOf(this, other)
		}

		if (!timeSpan.isOverlap(other.timeSpan)) {
			return if (state) listOf(this) else listOf(other)
		}

		val resultTimeSpans = if (state) timeSpan.exclude(other.timeSpan) else other.timeSpan.exclude(timeSpan)
		return resultTimeSpans
			.map { merge(other, it) }
			.toList()
	}

	fun join(other: IrrigationSpan): List<IrrigationSpan> {
		return if (state == other.state)
			union(other)
		else
			exclude(other)
	}

	fun toResponse(): UpcomingIrrigationItem {
		val section = sources
			.map { it.section }
			.first()

		val sources = sources
			.mapNotNull {
				return@mapNotNull if (it is IrrigationSource.SectionScheduleSource) {
					UpcomingIrrigationItem.Source(
						start = it.start,
						end = it.end,
						state = it.schedule.state,
						scheduleId = it.schedule.id,
					)
				} else if (it is IrrigationSource.SectionSlotSource) {
					UpcomingIrrigationItem.Source(
						start = it.start,
						end = it.end,
						state = true,
						slotId = it.slot.id,
					)
				} else {
					null
				}
			}

		return UpcomingIrrigationItem(
			start = timeSpan.start,
			end = timeSpan.end,
			sectionId = section.id,
			sectionName = section.name,
			sources = sources,
		)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as IrrigationSpan

		if (timeSpan != other.timeSpan) return false
		if (state != other.state) return false
		if (!sources.contentEquals(other.sources)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = timeSpan.hashCode()
		result = 31 * result + state.hashCode()
		result = 31 * result + sources.contentHashCode()
		return result
	}

	override fun toString(): String {
		return "IrrigationSpan(state=$state, timeSpan=$timeSpan)"
	}
}

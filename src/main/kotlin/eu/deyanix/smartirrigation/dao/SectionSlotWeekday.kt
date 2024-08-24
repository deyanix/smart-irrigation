package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.io.Serializable

@Entity
@IdClass(SectionSlotWeekday.SectionSlotWeekdayId::class)
data class SectionSlotWeekday(
	@Id
	@ManyToOne
	@JoinColumn
	var sectionSlot: SectionSlot,
	@Id
	var weekday: Int,
) {
	data class SectionSlotWeekdayId(
		var sectionSlot: Int? = null,
		var weekday: Int? = null,
	): Serializable
}


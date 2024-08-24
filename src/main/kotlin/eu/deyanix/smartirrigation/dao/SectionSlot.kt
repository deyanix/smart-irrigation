package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.LocalTime

@Entity
data class SectionSlot(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: LocalTime,
	var end: LocalTime,
	@OneToMany(mappedBy = "sectionSlot")
	var weekdays: MutableList<SectionSlotWeekday>,
)

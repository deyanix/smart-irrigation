package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.OffsetTime

@Entity
@Table(schema = "irrigation")
data class SectionSlot(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: OffsetTime,
	var end: OffsetTime,
	@OneToMany(mappedBy = "sectionSlot", cascade = [CascadeType.ALL], orphanRemoval = true)
	var weekdays: MutableList<SectionSlotWeekday> = mutableListOf(),
)

package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.LocalTime

@Entity
data class SectionWindow(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: LocalTime,
	var end: LocalTime,
	@OneToMany(mappedBy = "sectionWindow")
	var weekdays: MutableList<SectionWindowWeekday>,
)

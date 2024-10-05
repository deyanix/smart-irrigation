package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "irrigation")
data class SectionSchedule(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: LocalDateTime,
	var end: LocalDateTime,
	var state: Boolean,
)

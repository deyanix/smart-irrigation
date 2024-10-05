package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(schema = "irrigation")
data class SectionSchedule(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: ZonedDateTime,
	var end: ZonedDateTime,
	var state: Boolean,
)

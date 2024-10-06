package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(schema = "irrigation")
data class SectionSchedule(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: OffsetDateTime,
	var end: OffsetDateTime,
	var state: Boolean,
)

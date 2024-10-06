package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(schema = "irrigation")
data class Irrigation(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: OffsetDateTime = OffsetDateTime.now(),
	var end: OffsetDateTime = OffsetDateTime.now(),
	var finished: Boolean = false,
)

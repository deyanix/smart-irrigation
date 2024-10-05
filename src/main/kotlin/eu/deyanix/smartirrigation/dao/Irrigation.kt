package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(schema = "irrigation")
data class Irrigation(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: ZonedDateTime = ZonedDateTime.now(),
	var end: ZonedDateTime = ZonedDateTime.now(),
	var finished: Boolean = false,
)

package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "irrigation")
data class Irrigation(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int? = null,
	@ManyToOne
	@JoinColumn
	var section: Section,
	var start: LocalDateTime = LocalDateTime.now(),
	var end: LocalDateTime = LocalDateTime.now(),
	var finished: Boolean = false,
)

package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Irrigation(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	@ManyToOne
	@JoinColumn
	var installationSection: InstallationSection,
	var start: LocalDateTime = LocalDateTime.now(),
	var end: LocalDateTime = LocalDateTime.now(),
	var finished: Boolean = false,
)

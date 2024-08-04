package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
data class InstallationSection(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	var name: String,
	var gpio: Int,
	@ManyToOne
	@JoinColumn(name="installation_id")
	var installation: Installation,
	var installationIndex: Int,
)

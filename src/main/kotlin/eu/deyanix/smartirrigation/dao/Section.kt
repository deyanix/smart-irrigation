package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
@Table(schema = "irrigation")
data class Section(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	var name: String,
	var gpio: Int,
	@ManyToOne
	@JoinColumn
	var installation: Installation,
	var index: Int,
)

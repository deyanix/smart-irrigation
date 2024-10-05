package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
@Table(schema = "irrigation")
class Installation (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	var name: String,
)

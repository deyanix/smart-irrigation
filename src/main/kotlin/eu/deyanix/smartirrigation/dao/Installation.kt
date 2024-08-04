package eu.deyanix.smartirrigation.dao

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Installation (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Int,
	var name: String,
)

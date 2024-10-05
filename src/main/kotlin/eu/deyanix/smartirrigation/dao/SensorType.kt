package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
@Table(schema = "sensor")
data class SensorType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var name: String,
)
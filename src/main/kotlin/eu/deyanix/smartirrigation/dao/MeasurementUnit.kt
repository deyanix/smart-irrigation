package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
@Table(schema = "sensor")
data class MeasurementUnit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    var name: String,
    val symbol: String,
)
package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*

@Entity
@Table(schema = "sensor")
data class SensorItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    var name: String,
    val key: String,
    @ManyToOne
    @JoinColumn
    var sensor: Sensor,
    @ManyToOne
    @JoinColumn
    var measurementUnit: MeasurementUnit,
    var measurementAdjustment: Int,
)
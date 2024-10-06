package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(schema = "sensor")
data class Measurement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @ManyToOne
    @JoinColumn
    var sensorItem: SensorItem,
    val date: OffsetDateTime,
    var value: Double,
)

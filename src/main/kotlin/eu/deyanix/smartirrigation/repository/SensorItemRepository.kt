package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Sensor
import eu.deyanix.smartirrigation.dao.SensorItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.stream.Stream

interface SensorItemRepository : JpaRepository<SensorItem, Int> {
	@Query("SELECT SI FROM SensorItem SI WHERE SI.key = :key AND SI.sensor = :sensor")
	fun findByKey(sensor: Sensor, key: String): Optional<SensorItem>

	@Query("SELECT SI FROM SensorItem SI WHERE SI.sensor = :sensor ORDER BY SI.id")
	fun findAllBySensor(sensor: Sensor): Stream<SensorItem>
}

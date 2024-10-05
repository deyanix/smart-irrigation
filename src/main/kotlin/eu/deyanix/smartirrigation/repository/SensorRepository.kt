package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Sensor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface SensorRepository : JpaRepository<Sensor, Int> {
	@Query("SELECT S FROM Sensor S WHERE S.key = :key AND S.sensorType.name = :sensorType")
	fun findByKey(sensorType: String, key: String): Optional<Sensor>
}
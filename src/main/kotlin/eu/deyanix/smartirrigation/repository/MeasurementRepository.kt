package eu.deyanix.smartirrigation.repository

import eu.deyanix.smartirrigation.dao.Measurement
import eu.deyanix.smartirrigation.dao.SensorItem
import eu.deyanix.smartirrigation.dto.MeasurementItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime
import java.util.stream.Stream

interface MeasurementRepository : JpaRepository<Measurement, Int> {
	@Query("SELECT new eu.deyanix.smartirrigation.dto.MeasurementItem(" +
			"	DATE_TRUNC('minute', m.date), " +
			"	AVG(m.value) " +
			") " +
			"FROM Measurement m " +
			"WHERE m.sensorItem = :sensorItem AND m.date BETWEEN :dateFrom AND :dateTo " +
			"GROUP BY 1 ORDER BY 1")
	fun findAllMinutely(sensorItem: SensorItem, dateFrom: OffsetDateTime, dateTo: OffsetDateTime): Stream<MeasurementItem>

	@Query("SELECT new eu.deyanix.smartirrigation.dto.MeasurementItem(" +
			"	DATE_TRUNC('hour', m.date), " +
			"	AVG(m.value) " +
			") " +
			"FROM Measurement m " +
			"WHERE m.sensorItem = :sensorItem AND m.date BETWEEN :dateFrom AND :dateTo " +
			"GROUP BY 1 ORDER BY 1")
	fun findAllHourly(sensorItem: SensorItem, dateFrom: OffsetDateTime, dateTo: OffsetDateTime): Stream<MeasurementItem>

	@Query("SELECT new eu.deyanix.smartirrigation.dto.MeasurementItem(" +
			"	DATE_TRUNC('day', m.date), " +
			"	AVG(m.value) " +
			") " +
			"FROM Measurement m " +
			"WHERE m.sensorItem = :sensorItem AND m.date BETWEEN :dateFrom AND :dateTo " +
			"GROUP BY 1 ORDER BY 1")
	fun findAllDaily(sensorItem: SensorItem, dateFrom: OffsetDateTime, dateTo: OffsetDateTime): Stream<MeasurementItem>
}

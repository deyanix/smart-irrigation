package eu.deyanix.smartirrigation.service

import com.jayway.jsonpath.JsonPath
import eu.deyanix.smartirrigation.dao.Measurement
import eu.deyanix.smartirrigation.dao.Sensor
import eu.deyanix.smartirrigation.repository.MeasurementRepository
import eu.deyanix.smartirrigation.repository.SensorItemRepository
import eu.deyanix.smartirrigation.repository.SensorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class SenseCapService(
	private val sensorRepository: SensorRepository,
	private val measurementRepository: MeasurementRepository,
	private val sensorItemRepository: SensorItemRepository,
) {
	@Transactional
	fun handleMessage(message: String) {
		val context = JsonPath.parse(message)

		val deviceId = context.read<String>("$.end_device_ids.device_id")
		val sensor = sensorRepository.findByKey("SenseCAP", deviceId)
			.getOrNull() ?: return

		val receivedAtString = context.read<String>("$.received_at")
		val receivedAt = ZonedDateTime.parse(receivedAtString)

		context.read<List<Any>>("$.uplink_message.decoded_payload.messages")
			.forEach {
				handleMeasurement(sensor, receivedAt.toLocalDateTime(), it)
			}
	}

	@Transactional
	fun handleMeasurement(sensor: Sensor, date: LocalDateTime, message: Any) {
		val context = JsonPath.parse(message)

		val key = when (context.read<String?>("$.type")) {
			"report_telemetry" -> when (context.read<Int?>("$.measurementId")) {
				4102 -> "temperature"
				4103 -> "moisture"
				4108 -> "conductivity"
				else -> return
			}
			"battery" -> "battery"
			else -> return
		}

		val sensorItem = sensorItemRepository.findByKey(sensor, key)
			.getOrNull() ?: return

		val value = when (key) {
			"temperature", "moisture", "conductivity" -> context.read<Any>("$.measurementValue")
			"battery" -> context.read<Any>("$.battery")
			else -> return
		}

		measurementRepository.saveAndFlush(Measurement(
			sensorItem = sensorItem,
			date = date,
			value = when (value) {
				is Double -> value
				is Int -> value.toDouble()
				is String -> value.toDouble()
				else -> return
			},
		))
	}
}
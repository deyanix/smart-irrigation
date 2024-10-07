package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Sensor
import eu.deyanix.smartirrigation.dto.SensorItemResponse
import eu.deyanix.smartirrigation.dto.SensorResponse
import eu.deyanix.smartirrigation.repository.InstallationRepository
import eu.deyanix.smartirrigation.repository.MeasurementRepository
import eu.deyanix.smartirrigation.repository.SensorItemRepository
import eu.deyanix.smartirrigation.repository.SensorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class SensorService(
	private val sensorRepository: SensorRepository,
	private val installationRepository: InstallationRepository,
	private val sensorItemRepository: SensorItemRepository,
	private val measurementRepository: MeasurementRepository
) {
	@Transactional
	fun getSensors(installationId: Int): List<SensorResponse> {
		val installation = installationRepository.findById(installationId)
			.orElseThrow()

		return sensorRepository.findAllByInstallation(installation)
			.map { sensor ->
				SensorResponse(
					id = sensor.id,
					name = sensor.name,
					typeId = sensor.sensorType.id,
					typeName = sensor.sensorType.name,
					items = getSensorItems(sensor)
				)
			}
			.toList()
	}

	@Transactional
	fun getSensorItems(sensor: Sensor): List<SensorItemResponse> {
		return sensorItemRepository.findAllBySensor(sensor)
			.map { sensorItem ->
				val measurement = measurementRepository.findLastBySensorItem(sensorItem)
					.getOrNull()

				return@map SensorItemResponse(
					id =  sensorItem.id,
					name = sensorItem.name,
					measurementUnitId = sensorItem.measurementUnit.id,
					measurementUnitName = sensorItem.measurementUnit.name,
					measurementUnitSymbol = sensorItem.measurementUnit.symbol,
					lastMeasurementDate = measurement?.date,
					lastMeasurementValue = measurement?.value,
				)
			}
			.toList()
	}
}

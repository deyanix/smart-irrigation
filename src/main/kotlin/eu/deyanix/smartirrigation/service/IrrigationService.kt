package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class IrrigationService(
	private val irrigationRepository: IrrigationRepository,
	private val gpioService: GpioService,
) {
	fun refresh(installationId: Int) {
		irrigationRepository.findAllUnfinished(installationId)
			.groupBy(Irrigation::installationSection)
			.forEach {(installationSection, irrigations) ->
				val state = gpioService.getState(installationSection.gpio)
				irrigations.forEach {
					if (state) {
						it.end = LocalDateTime.now()
					} else {
						it.finished = true
					}
				}
				irrigationRepository.saveAll(irrigations)
			}
		irrigationRepository.flush()
	}
}

package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Irrigation
import eu.deyanix.smartirrigation.dao.Section
import eu.deyanix.smartirrigation.repository.IrrigationRepository
import eu.deyanix.smartirrigation.repository.SectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SectionValveService(
	private val gpioService: GpioService,
	private val irrigationRepository: IrrigationRepository,
	private val sectionRepository: SectionRepository,
	private val irrigationService: IrrigationService,
) {
	fun setOpen(section: Section, irrigating: Boolean) =
		gpioService.setState(section.gpio, !irrigating)

	fun isOpen(section: Section): Boolean =
		!gpioService.getState(section.gpio)

	@Transactional
	fun start(section: Section) {
		setOpen(section, true)

		val irrigations = irrigationRepository.findAllUnfinishedBySection(section).toList().toMutableList()
		if (irrigations.any()) {
			irrigations.forEach { it.finished = true }
		} else {
			irrigations.add(Irrigation(section = section))
		}

		irrigations.first().apply {
			finished = false
			end = LocalDateTime.now()
		}

		irrigationRepository.saveAllAndFlush(irrigations)
	}

	@Transactional
	fun stop(section: Section) {
		setOpen(section, false)
		val irrigations = irrigationRepository.findAllUnfinishedBySection(section).toList()
		irrigations.forEach { it.finished = true }

		irrigationRepository.saveAllAndFlush(irrigations)
	}

	@Transactional
	fun resetWithGpio() {
		sectionRepository.findAll()
			.forEach { section ->
				setOpen(section, false)
				stop(section)
			}
	}

	@Transactional
	fun synchronizeGpio(section: Section) {
		val shouldState = irrigationService.getCurrentIrrigations(section).isNotEmpty()

		if (shouldState) {
			start(section)
		} else {
			stop(section)
		}
	}

	@Transactional
	fun synchronizeGpio() {
		sectionRepository.findAll()
			.forEach { section ->
				synchronizeGpio(section)
			}
	}
}

package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.Section
import org.springframework.stereotype.Service

@Service
class SectionValveService(
	private val gpioService: GpioService
) {
	fun setOpen(section: Section, irrigating: Boolean) =
		gpioService.setState(section.gpio, irrigating)

	fun isOpen(section: Section): Boolean =
		gpioService.getState(section.gpio)
}

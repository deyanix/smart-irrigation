package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dto.SectionScheduleRequest
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import org.springframework.stereotype.Service

@Service
class SectionScheduleService(
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val sectionRepository: SectionRepository
) {
	fun create(installationId: Int, sectionIndex: Int, request: SectionScheduleRequest) {
		val schedule = SectionSchedule(
			section = sectionRepository.findByIndex(installationId, sectionIndex)
				.orElseThrow(),
			start = request.start,
			end = request.end,
			state = request.state,
		)

		sectionScheduleRepository.saveAndFlush(schedule)
	}

	fun delete(scheduleId: Int) {
		sectionScheduleRepository.deleteById(scheduleId)
	}
}

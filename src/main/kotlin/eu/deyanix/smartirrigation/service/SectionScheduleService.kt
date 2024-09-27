package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dto.*
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionScheduleService(
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val sectionRepository: SectionRepository,
	private val sectionValveService: SectionValveService,
) {
	fun search(sectionId: Int, criteria: SectionScheduleCriteria): SearchResponse<SectionSchedule> {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val result = sectionScheduleRepository.findPageBySectionBetween(
			section,
			criteria.from,
			criteria.to,
			PageRequest.of(criteria.page, criteria.pageSize))

		return SearchResponse(result)
	}

	@Transactional
	fun create(sectionId: Int, request: SectionScheduleRequest) {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val schedule = SectionSchedule(
			section = section,
			start = request.start,
			end = request.end,
			state = request.state,
		)

		ensureSchedule(schedule)
		sectionScheduleRepository.saveAndFlush(schedule)
		sectionValveService.synchronizeGpio(section)
	}

	fun delete(scheduleId: Int) {
		val schedule = sectionScheduleRepository.findById(scheduleId)
			.orElseThrow()

		sectionScheduleRepository.delete(schedule)
		sectionValveService.synchronizeGpio(schedule.section)
	}

	@Transactional
	fun ensureSchedule(schedule: SectionSchedule) {
		val schedules = sectionScheduleRepository
			.findAllBySectionInTime(schedule.section, schedule.start, schedule.end, !schedule.state)
			.toList()
		sectionScheduleRepository.deleteAll(schedules)
	}
}

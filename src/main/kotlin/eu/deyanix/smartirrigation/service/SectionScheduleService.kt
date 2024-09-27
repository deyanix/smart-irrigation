package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.SectionSchedule
import eu.deyanix.smartirrigation.dto.SearchResponse
import eu.deyanix.smartirrigation.dto.SectionScheduleCriteria
import eu.deyanix.smartirrigation.dto.SectionScheduleRequest
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionScheduleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SectionScheduleService(
	private val sectionScheduleRepository: SectionScheduleRepository,
	private val sectionRepository: SectionRepository,
	private val sectionService: SectionService
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

	fun create(sectionId: Int, request: SectionScheduleRequest) {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val schedule = SectionSchedule(
			section = section,
			start = request.start,
			end = request.end,
			state = request.state,
		)

		sectionScheduleRepository.saveAndFlush(schedule)
		sectionService.synchronizeGpio(section)
	}

	fun delete(scheduleId: Int) {
		val schedule = sectionScheduleRepository.findById(scheduleId)
			.orElseThrow()

		sectionScheduleRepository.delete(schedule)
		sectionService.synchronizeGpio(schedule.section)
	}
}

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
	private val sectionRepository: SectionRepository
) {
	fun search(installationId: Int, sectionIndex: Int, criteria: SectionScheduleCriteria): SearchResponse<SectionSchedule> {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()
		val result = sectionScheduleRepository.findPageBySectionBetween(
			section,
			criteria.from,
			criteria.to,
			PageRequest.of(criteria.page, criteria.pageSize))

		return SearchResponse(result)
	}

	fun create(installationId: Int, sectionIndex: Int, request: SectionScheduleRequest) {
		val section = sectionRepository.findByIndex(installationId, sectionIndex)
			.orElseThrow()

		val schedule = SectionSchedule(
			section = section,
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

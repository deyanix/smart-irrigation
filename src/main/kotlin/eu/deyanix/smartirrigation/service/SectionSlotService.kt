package eu.deyanix.smartirrigation.service

import eu.deyanix.smartirrigation.dao.SectionSlot
import eu.deyanix.smartirrigation.dto.SectionSlotListDTO
import eu.deyanix.smartirrigation.dto.SectionSlotRequest
import eu.deyanix.smartirrigation.repository.SectionRepository
import eu.deyanix.smartirrigation.repository.SectionSlotRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

@Service
class SectionSlotService(
	private val sectionSlotRepository: SectionSlotRepository,
	private val sectionRepository: SectionRepository,
	private val sectionValveService: SectionValveService,
) {
	@Transactional(readOnly = true)
	fun getList(sectionId: Int): Stream<SectionSlotListDTO> {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		return sectionSlotRepository.findAllBySection(section)
			.map(::SectionSlotListDTO)
	}

	@Transactional
	fun create(sectionId: Int, request: SectionSlotRequest) {
		val section = sectionRepository.findById(sectionId)
			.orElseThrow()

		val slot = SectionSlot(
			section = section,
			start = request.start,
			end = request.end,
		)

		request.applyTo(slot)
		sectionSlotRepository.saveAndFlush(slot)
		sectionValveService.synchronizeGpio(section)
	}

	@Transactional
	fun update(slotId: Int, request: SectionSlotRequest) {
		val slot = sectionSlotRepository.findById(slotId)
			.orElseThrow()

		request.applyTo(slot)
		sectionSlotRepository.saveAndFlush(slot)
		sectionValveService.synchronizeGpio(slot.section)
	}

	@Transactional
	fun delete(slotId: Int) {
		val slot = sectionSlotRepository.findById(slotId)
			.orElseThrow()

		sectionSlotRepository.delete(slot)
		sectionValveService.synchronizeGpio(slot.section)
	}
}

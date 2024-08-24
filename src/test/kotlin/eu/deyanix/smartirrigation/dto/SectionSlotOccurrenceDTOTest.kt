package eu.deyanix.smartirrigation.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class SectionSlotOccurrenceDTOTest {
	@Test
	fun testAlreadyStarted() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.WEDNESDAY, LocalTime.of(4, 0, 0), LocalTime.of(6, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25))

		assertEquals(LocalDateTime.of(2024, 8, 7, 4, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.end)
	}

	@Test
	fun testAlreadyFinished() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.WEDNESDAY, LocalTime.of(3, 0, 0), LocalTime.of(5, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25))
		
		assertEquals(LocalDateTime.of(2024, 8, 14, 3, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 14, 5, 0), occurrence.end)
	}

	@Test
	fun testNotStartedToday() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.WEDNESDAY, LocalTime.of(6, 0, 0), LocalTime.of(8, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25))

		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 8, 0), occurrence.end)
	}

	@Test
	fun testStartedYesterday() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.TUESDAY, LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25))

		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.end)
	}

	@Test
	fun testNotStartedBetweenDays() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.WEDNESDAY, LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25))

		assertEquals(LocalDateTime.of(2024, 8, 7, 23, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 8, 6, 0), occurrence.end)
	}

	@Test
	fun testStartedUntilTomorrow() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.TUESDAY, LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 6, 23, 25))

		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.end)
	}

	@Test
	fun testNotStartedUntilTomorrow() {
		val builder = SectionSlotOccurrenceDTO.Builder(DayOfWeek.TUESDAY, LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0))
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 6, 22, 25))

		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.end)
	}
}

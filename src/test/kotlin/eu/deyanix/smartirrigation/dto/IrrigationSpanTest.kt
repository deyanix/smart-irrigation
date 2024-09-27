package eu.deyanix.smartirrigation.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class IrrigationSpanTest {
	@Test
	fun testAlreadyStarted() {
		val builder = IrrigationSpan.Builder(LocalTime.of(4, 0, 0), LocalTime.of(6, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25), DayOfWeek.WEDNESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 7, 4, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.span.end)
	}

	@Test
	fun testAlreadyFinished() {
		val builder = IrrigationSpan.Builder(LocalTime.of(3, 0, 0), LocalTime.of(5, 0, 0), false)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25), DayOfWeek.WEDNESDAY)

		assertEquals(false, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 14, 3, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 14, 5, 0), occurrence.span.end)
	}

	@Test
	fun testNotStartedToday() {
		val builder = IrrigationSpan.Builder(LocalTime.of(6, 0, 0), LocalTime.of(8, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25), DayOfWeek.WEDNESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 8, 0), occurrence.span.end)
	}

	@Test
	fun testStartedYesterday() {
		val builder = IrrigationSpan.Builder(LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25), DayOfWeek.TUESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.span.end)
	}

	@Test
	fun testNotStartedBetweenDays() {
		val builder = IrrigationSpan.Builder(LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 7, 5, 25), DayOfWeek.WEDNESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 7, 23, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 8, 6, 0), occurrence.span.end)
	}

	@Test
	fun testStartedUntilTomorrow() {
		val builder = IrrigationSpan.Builder(LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 6, 23, 25), DayOfWeek.TUESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.span.end)
	}

	@Test
	fun testNotStartedUntilTomorrow() {
		val builder = IrrigationSpan.Builder(LocalTime.of(23, 0, 0), LocalTime.of(6, 0, 0), true)
		val occurrence = builder.next(LocalDateTime.of(2024, 8, 6, 22, 25), DayOfWeek.TUESDAY)

		assertEquals(true, occurrence.state)
		assertEquals(LocalDateTime.of(2024, 8, 6, 23, 0), occurrence.span.start)
		assertEquals(LocalDateTime.of(2024, 8, 7, 6, 0), occurrence.span.end)
	}
}

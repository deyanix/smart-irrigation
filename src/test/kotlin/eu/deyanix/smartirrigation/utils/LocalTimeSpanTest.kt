package eu.deyanix.smartirrigation.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LocalTimeSpanTest {
	@Test
	fun testSumExclusive() {
		val span1 = LocalTimeSpan(
			LocalDateTime.of(2024, 5, 12, 12, 50, 0),
			LocalDateTime.of(2024, 5, 12, 13, 10, 0))
		val span2 = LocalTimeSpan(
			LocalDateTime.of(2024, 5, 12, 14, 0, 0),
			LocalDateTime.of(2024, 5, 12, 14, 10, 0))

		val result = span1.sum(span2)

		assertEquals(null, result)
	}

	@Test
	fun testSumOverlapping() {
		val span1 = LocalTimeSpan(
			LocalDateTime.of(2024, 5, 12, 12, 50, 0),
			LocalDateTime.of(2024, 5, 12, 13, 10, 0))
		val span2 = LocalTimeSpan(
			LocalDateTime.of(2024, 5, 12, 13, 0, 0),
			LocalDateTime.of(2024, 5, 12, 14, 30, 0))

		val result = span1.sum(span2)

		assertEquals(LocalDateTime.of(2024, 5, 12, 12, 50, 0), result?.start)
		assertEquals(LocalDateTime.of(2024, 5, 12, 14, 30, 0), result?.end)
	}
}

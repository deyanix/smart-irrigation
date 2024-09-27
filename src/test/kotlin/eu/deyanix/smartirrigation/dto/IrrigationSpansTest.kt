package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class IrrigationSpansTest {
	@Test
	fun testFlatten() {
		val spans = IrrigationSpans(
			spans = listOf(
				LocalTimeSpan(
					LocalDateTime.of(2024, 4, 12, 12, 30, 0),
					LocalDateTime.of(2024, 4, 12, 12, 50, 0)
				),
				LocalTimeSpan(
					LocalDateTime.of(2024, 4, 12, 13, 15, 0),
					LocalDateTime.of(2024, 4, 12, 13, 25, 0)
				),
				LocalTimeSpan(
					LocalDateTime.of(2024, 4, 12, 13, 20, 0),
					LocalDateTime.of(2024, 4, 12, 13, 40, 0)
				)
			),
			state = true
		)

		val result = spans.flatten()

		assertEquals(true, result.state)
		assertEquals(2, result.spans.size)
	}
}

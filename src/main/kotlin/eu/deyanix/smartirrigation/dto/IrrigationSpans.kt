package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import java.time.LocalTime
import java.util.*

class IrrigationSpans(
	val spans: List<LocalTimeSpan>,
	val state: Boolean,
) {
	companion object {
		fun empty(state: Boolean = true) =
			 IrrigationSpans(spans = emptyList(), state = state)

		fun flatten(spans: List<IrrigationSpans>): IrrigationSpans {
			return spans
				.groupBy { it.state }
				.map { IrrigationSpans(it.value.flatMap { it.spans }, it.key) }
				.reduceOrNull { s1, s2 -> s1.merge(s2) }
				?.flatten()
				?: empty()
		}

		fun of(spans: List<IrrigationSpan>): IrrigationSpans {
			return spans
				.groupBy { it.state }
				.map { IrrigationSpans(it.value.map { it.span }, it.key) }
				.reduceOrNull { s1, s2 -> s1.merge(s2) }
				?.flatten()
				?: empty()
		}
	}

	fun minTime() =
		spans.minOfOrNull { it.start }?.toLocalDate()?.atTime(LocalTime.MIN)

	fun maxTime() =
		spans.maxOfOrNull { it.end }?.toLocalDate()?.atTime(LocalTime.MAX)

	fun merge(other: IrrigationSpans): IrrigationSpans {
		val newSpans = spans.stream()
			.flatMap { span1 ->
				other.spans.stream()
					.flatMap innerMap@{ span2 ->
						if (state == other.state) {
							return@innerMap Arrays.stream(span1.union(span2))
						}

						if (state) {
							return@innerMap Arrays.stream(span1.exclude(span2))
						} else {
							return@innerMap Arrays.stream(span2.exclude(span1))
						}
					}
			}
			.toList()


		return IrrigationSpans(spans = newSpans, state = state)
	}

	fun flatten(): IrrigationSpans {
		val newSpans = spans
			.sortedBy { it.start }
			.fold(mutableListOf<LocalTimeSpan>()) { consolidated, next ->
				if (consolidated.isEmpty()) {
					consolidated.add(next)
				} else {
					val current = consolidated.last()
					val sum = current.sum(next)

					if (sum != null) {
						consolidated.removeLast()
						consolidated.add(sum)
					} else {
						consolidated.add(next)
					}
				}

				return@fold consolidated
			}

		return IrrigationSpans(spans = newSpans, state = state)
	}
}

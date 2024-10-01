package eu.deyanix.smartirrigation.dto

import eu.deyanix.smartirrigation.utils.LocalTimeSpan
import java.time.LocalDateTime

class IrrigationSpanCollection(
	val spans: List<IrrigationSpan>,
) {
	companion object {
		fun empty() =
			 IrrigationSpanCollection(spans = emptyList())

		fun ofCollections(spans: List<IrrigationSpanCollection>): IrrigationSpanCollection {
			return spans
				.map { IrrigationSpanCollection(it.spans) }
				.reduceOrNull { s1, s2 -> s1.merge(s2) }
				?.flatten()
				?: empty()
		}

		fun ofCollections(vararg spans: IrrigationSpanCollection): IrrigationSpanCollection {
			return ofCollections(spans.asList())
		}

		fun ofSpans(spans: List<IrrigationSpan>): IrrigationSpanCollection {
			return ofCollections(IrrigationSpanCollection(spans))
		}

		fun ofSpans(vararg span: IrrigationSpan): IrrigationSpanCollection {
			return ofSpans(span.asList())
		}
	}

	fun merge(other: IrrigationSpanCollection): IrrigationSpanCollection {
		val mergedSpans = (spans + other.spans)
		return IrrigationSpanCollection(
			spans = mergedSpans
				.drop(1)
				.fold(mergedSpans.take(1)) { consolidated, next ->
					consolidated
						.flatMap { it.join(next) }
						.distinct()
				}
		)
	}

	fun flatten(): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans
				.sortedBy { it.timeSpan.start }
				.fold(mutableListOf()) { consolidated, next ->
					if (consolidated.isEmpty()) {
						consolidated.add(next)
					} else {
						val current = consolidated.last()
						val sum = current.union(next)

						if (sum.size == 1) {
							consolidated.removeLast()
							consolidated.add(sum.first())
						} else {
							consolidated.add(next)
						}
					}

					return@fold consolidated
				}
		)
	}

	fun onlyWhen(date: LocalDateTime): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans.filter { it.timeSpan.isBetween(date) }
		)
	}

	fun onlyNow(): IrrigationSpanCollection {
		return onlyWhen(LocalDateTime.now())
	}

	fun onlyWhen(span: LocalTimeSpan): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans
				.filter { it.timeSpan.isOverlap(span) }
		)
	}

	fun onlyWhen(start: LocalDateTime, end: LocalDateTime): IrrigationSpanCollection {
		return onlyWhen(LocalTimeSpan(start, end))
	}

	fun onlyBefore(date: LocalDateTime): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans
				.filter { it.timeSpan.start <= date }
		)
	}

	fun onlyAfter(date: LocalDateTime): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans
				.filter { it.timeSpan.end >= date }
		)
	}

	fun onlyState(state: Boolean): IrrigationSpanCollection {
		return IrrigationSpanCollection(
			spans = spans
				.filter { it.state == state }
		)
	}

	fun isEmpty(): Boolean {
		return spans.isEmpty()
	}

	fun isNonEmpty(): Boolean {
		return !isEmpty()
	}

	override fun toString(): String {
		return "IrrigationSpanCollection(spans=$spans)"
	}


}

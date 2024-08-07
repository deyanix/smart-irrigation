package eu.deyanix.smartirrigation.dao

import jakarta.persistence.*
import java.io.Serializable

@Entity
@IdClass(SectionWindowWeekdayId::class)
data class SectionWindowWeekday(
	@Id
	@ManyToOne
	@JoinColumn
	var sectionWindow: SectionWindow,
	@Id
	var weekday: Int,
)

data class SectionWindowWeekdayId(
	var sectionWindow: Int? = null,
	var weekday: Int? = null,
): Serializable

package eu.deyanix.smartirrigation.dto

import org.springframework.data.domain.Page

data class SearchResponse<T>(
	val data: List<T>,
	val totalRows: Long,
) {
	constructor(page: Page<T>) : this(page.content, page.totalElements) {
	}
}

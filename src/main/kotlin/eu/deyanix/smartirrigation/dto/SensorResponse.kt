package eu.deyanix.smartirrigation.dto

class SensorResponse(
	var id: Int,
	var name: String,
	var typeId: Int?,
	var typeName: String,
	var items: List<SensorItemResponse>,
)

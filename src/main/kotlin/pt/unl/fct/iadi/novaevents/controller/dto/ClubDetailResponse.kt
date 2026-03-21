package pt.unl.fct.iadi.novaevents.controller.dto

data class ClubDetailResponse(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val events: List<EventResponse>
)
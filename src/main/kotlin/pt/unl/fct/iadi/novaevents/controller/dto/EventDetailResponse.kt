package pt.unl.fct.iadi.novaevents.controller.dto

import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate

data class EventDetailResponse(
    val id: Long,
    val clubId: Long,
    val clubName: String,
    val name: String,
    val date: LocalDate,
    val location: String?,
    val type: EventType,
    val description: String?
)
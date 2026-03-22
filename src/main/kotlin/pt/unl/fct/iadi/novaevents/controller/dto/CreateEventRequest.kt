package pt.unl.fct.iadi.novaevents.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate

data class CreateEventRequest(
    @field:NotBlank(message = "Name is required")
    val name : String,
    @field:NotNull(message = "Date is required")
    val date : LocalDate,
    val location : String?,
    @field:NotNull(message = "Event type is required")
    val type : EventType,
    val description : String?
)

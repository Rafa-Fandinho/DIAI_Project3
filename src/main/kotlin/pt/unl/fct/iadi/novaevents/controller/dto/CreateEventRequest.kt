package pt.unl.fct.iadi.novaevents.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class CreateEventRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
    @field:NotNull(message = "Date is required")
    val date: LocalDate,
    val location: String?,
    val typeId: Long?,
    val description: String?
)

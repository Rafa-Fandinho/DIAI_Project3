package pt.unl.fct.iadi.novaevents.controller

import jakarta.validation.Valid
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate

@RequestMapping
interface NovaeventsAPI {

    @GetMapping("/clubs")
    fun listClubs(model: Model): String

    @GetMapping("/clubs/{clubId}")
    fun viewClubDetails(
        @PathVariable clubId: Long,
        model: Model
    ): String

    @GetMapping("/events")
    fun listEvents(
        @RequestParam(required = false) type: EventType?,
        @RequestParam(required = false) clubId: Long?,
        model: Model
    ): String

    @GetMapping("/clubs/{clubId}/events/{eventId}")
    fun viewEventDetails(
        @PathVariable clubId: Long,
        @PathVariable eventId: Long,
        model:Model
    ): String

    @GetMapping("/clubs/{clubId}/events/new")
    fun showCreateForm(
        @PathVariable clubId: Long,
        model: Model
    ): String

    @PostMapping("/clubs/{clubId}/events")
    fun createEvent(
        @PathVariable clubId: Long,
        @Valid request: CreateEventRequest,
        bindingResult: BindingResult,
        model: Model
    ): String

   @GetMapping("/clubs/{clubId}/events/{eventId}/edit")
   fun showEditForm(
       @PathVariable clubId: Long,
       @PathVariable eventId: Long,
       model: Model
   ): String

    @PostMapping("/clubs/{clubId}/events/{eventId}")
    fun editEvent(
        @PathVariable clubId: Long,
        @PathVariable eventId: Long,
        @Valid request: CreateEventRequest,
        bindingResult: BindingResult,
        model: Model
    ): String

    @GetMapping("/clubs/{clubId}/events/{eventId}/delete")
    fun confirmDelete(
        @PathVariable clubId: Long,
        @PathVariable eventId: Long,
        model: Model
    ): String

    @PostMapping("clubs/{clubId}/events/{eventId}/delete")
    fun deleteEvent(
        @PathVariable clubId: Long,
        @PathVariable eventId: Long,
    ): String
}
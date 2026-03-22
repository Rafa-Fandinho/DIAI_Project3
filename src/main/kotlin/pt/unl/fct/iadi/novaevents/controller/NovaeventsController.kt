package pt.unl.fct.iadi.novaevents.controller

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.model.EventType
import pt.unl.fct.iadi.novaevents.service.EventAlreadyExistsException
import pt.unl.fct.iadi.novaevents.service.NovaeventsService
import java.time.LocalDate

@Controller
class NovaeventsController (private val service: NovaeventsService) : NovaeventsAPI {
    override fun listClubs(model: Model): String {
        model.addAttribute("clubs",service.listClubs())
        return "clubs/list"
    }

    override fun viewClubDetails(clubId: Long, model: Model): String {
        model.addAttribute("club",service.viewClubDetails(clubId))
        return "clubs/detail"
    }

    override fun listEvents(type: EventType?, clubId: Long?, model: Model): String {
        model.addAttribute("events", service.listEvents(type, clubId))
        model.addAttribute("clubs", service.listClubs())
        return "events/list"
    }

    override fun viewEventDetails(clubId: Long, eventId: Long, model: Model): String {
        model.addAttribute("event",service.viewEventDetails(clubId, eventId))
        return "events/detail"
    }

    override fun showCreateForm(clubId: Long, model: Model): String {
        model.addAttribute("event", CreateEventRequest( "", LocalDate.now(), null, EventType.WORKSHOP, null))
        model.addAttribute("clubId",clubId)
        return "events/create"
    }

    override fun createEvent(clubId: Long, @Valid request: CreateEventRequest, bindingResult: BindingResult, model: Model): String {
        if(bindingResult.hasErrors()){
            model.addAttribute("clubId", clubId)
            return "events/create"
        }
        val eventId = try {
            service.createEvent(clubId, request)
        } catch (e: EventAlreadyExistsException) {
            bindingResult.rejectValue("name", "error.name", "An event with this name already exists")
            model.addAttribute("clubId", clubId)
            return "events/create"
        }
        return "redirect:/clubs/$clubId/events/$eventId"
    }

    override fun showEditForm(clubId: Long, eventId: Long, model: Model): String {
        val event = service.viewEventDetails(clubId,eventId)

        val form = CreateEventRequest(
            name = event.name,
            date = event.date,
            location = event.location,
            type = event.type,
            description = event.description
        )
        model.addAttribute("event", form)
        model.addAttribute("clubId", clubId)
        model.addAttribute("eventId", eventId)
        return "events/edit"
    }

    override fun editEvent(clubId: Long, eventId: Long, @Valid request: CreateEventRequest, bindingResult: BindingResult, model: Model): String{
        if(bindingResult.hasErrors()){
            model.addAttribute("clubId", clubId)
            model.addAttribute("eventId", eventId)
            return "events/edit"
        }
        try {
            service.editEvent(clubId, eventId, request)
        } catch (e: EventAlreadyExistsException) {
            bindingResult.rejectValue("name", "error.name", "An event with this name already exists")
            model.addAttribute("clubId", clubId)
            model.addAttribute("eventId", eventId)
            return "events/edit"
        }
        return "redirect:/clubs/$clubId/events/$eventId"
    }

    override fun confirmDelete(clubId: Long, eventId: Long, model: Model): String {
        model.addAttribute("event",service.viewEventDetails(clubId, eventId))
        return "events/delete"
    }

    override fun deleteEvent(clubId: Long, eventId: Long): String {
        service.deleteEvent(clubId, eventId)
        return "redirect:/clubs/$clubId"
    }


}
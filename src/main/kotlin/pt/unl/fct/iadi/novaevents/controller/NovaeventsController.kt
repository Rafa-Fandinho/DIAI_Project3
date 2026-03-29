package pt.unl.fct.iadi.novaevents.controller

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.repository.EventTypeRepository
import pt.unl.fct.iadi.novaevents.service.ClubService
import pt.unl.fct.iadi.novaevents.service.EventAlreadyExistsException
import pt.unl.fct.iadi.novaevents.service.EventService
import java.time.LocalDate

@Controller
class NovaeventsController (
    private val clubService: ClubService,
    private val eventService: EventService,
    private val eventTypeRepository: EventTypeRepository
) : NovaeventsAPI {
    override fun listClubs(model: Model): String {
        model.addAttribute("clubs",clubService.findAllWithCounts())
        return "clubs/list"
    }

    override fun viewClubDetails(clubId: Long, model: Model): String {
        model.addAttribute("club",clubService.findById(clubId))
        return "clubs/detail"
    }

    override fun listEvents(typeId: Long?, clubId: Long?, from: LocalDate?, to: LocalDate?, model: Model): String {
        model.addAttribute("events", eventService.listEvents(clubId, typeId, from, to))
        model.addAttribute("clubs", clubService.findAll())
        model.addAttribute("types", eventTypeRepository.findAll())
        return "events/list"
    }

    override fun viewEventDetails(clubId: Long, eventId: Long, model: Model): String {
        model.addAttribute("event",eventService.viewEventDetails(eventId))
        return "events/detail"
    }

    override fun showCreateForm(clubId: Long, model: Model): String {
        model.addAttribute("event", CreateEventRequest( "", LocalDate.now(), null, 0, null))
        model.addAttribute("clubId",clubId)
        return "events/create"
    }

    override fun createEvent(clubId: Long, @Valid request: CreateEventRequest, bindingResult: BindingResult, model: Model): String {
        if(bindingResult.hasErrors()){
            model.addAttribute("event", request)
            model.addAttribute("clubId", clubId)
            return "events/create"
        }
        val eventId = try {
            eventService.createEvent(clubId, request)
        } catch (e: EventAlreadyExistsException) {
            bindingResult.rejectValue("name", "error.name", "An event with this name already exists")
            model.addAttribute("event", request)
            model.addAttribute("clubId", clubId)
            return "events/create"
        }
        return "redirect:/clubs/$clubId/events/$eventId"
    }

    override fun showEditForm(clubId: Long, eventId: Long, model: Model): String {
        val event = eventService.viewEventDetails(eventId)

        val form = CreateEventRequest(
            name = event.name,
            date = event.date,
            location = event.location,
            typeId = event.type.id,
            description = event.description
        )
        model.addAttribute("event", form)
        model.addAttribute("clubId", clubId)
        model.addAttribute("eventId", eventId)
        return "events/edit"
    }

    override fun editEvent(clubId: Long, eventId: Long, @Valid request: CreateEventRequest, bindingResult: BindingResult, model: Model): String{
        if(bindingResult.hasErrors()){
            model.addAttribute("event", request)
            model.addAttribute("clubId", clubId)
            model.addAttribute("eventId", eventId)
            return "events/edit"
        }
        try {
            eventService.editEvent(eventId, request)
        } catch (e: EventAlreadyExistsException) {
            bindingResult.rejectValue("name", "error.name", "An event with this name already exists")
            model.addAttribute("event", request)
            model.addAttribute("clubId", clubId)
            model.addAttribute("eventId", eventId)
            return "events/edit"
        }
        return "redirect:/clubs/$clubId/events/$eventId"
    }

    override fun confirmDelete(clubId: Long, eventId: Long, model: Model): String {
        model.addAttribute("event",eventService.viewEventDetails(eventId))
        return "events/delete"
    }

    override fun deleteEvent(clubId: Long, eventId: Long): String {
        eventService.deleteEvent(eventId)
        return "redirect:/clubs/$clubId"
    }


}
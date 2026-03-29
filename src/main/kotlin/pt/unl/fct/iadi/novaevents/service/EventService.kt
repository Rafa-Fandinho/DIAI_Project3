package pt.unl.fct.iadi.novaevents.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.model.Event
import pt.unl.fct.iadi.novaevents.repository.ClubRepository
import pt.unl.fct.iadi.novaevents.repository.EventRepository
import pt.unl.fct.iadi.novaevents.repository.EventTypeRepository
import java.time.LocalDate

@Service
class EventService(
    private val clubRepository: ClubRepository,
    private val eventRepository: EventRepository,
    private val eventTypeRepository : EventTypeRepository
) {
    fun listEvents(clubId: Long?, typeId: Long?, from: LocalDate?, to: LocalDate?): List<Event>{
        val type = typeId?.let {
            eventTypeRepository.findById(it)
                .orElseThrow { EventTypeNotFoundException(typeId) }
        }
        return when{
            clubId != null && type != null && from != null && to != null ->
                eventRepository.findByClubIdAndTypeAndDateBetween(clubId,type, from, to)
            clubId != null && type != null ->
                eventRepository.findByClubIdAndType(clubId,type)
            clubId != null ->
                eventRepository.findByClubId(clubId)
            type != null ->
                eventRepository.findByType(type)
            from != null && to != null ->
                eventRepository.findByDateBetween(from,to)
            else->
                eventRepository.findAll()
        }
    }

    fun viewEventDetails(eventId: Long): Event {
        val event = eventRepository.findById(eventId)
            .orElseThrow { EventNotFoundException(eventId) }
        return event
    }

    fun createEvent(clubId: Long, request: CreateEventRequest): Long{
        if(eventRepository.existsByNameIgnoreCase(request.name)){
            throw EventAlreadyExistsException(request.name)
        }
        val club = clubRepository.findById(clubId)
            .orElseThrow { ClubNotFoundException(clubId) }
        val type = eventTypeRepository.findById(request.typeId!!)
            .orElseThrow { EventTypeNotFoundException(request.typeId) }
        val event = Event(
            club = club,
            name = request.name,
            date = request.date,
            location = request.location,
            type = type,
            description = request.description
        )
        return eventRepository.save(event).id!!
    }

    fun editEvent(eventId: Long, request: CreateEventRequest){
        val existing = viewEventDetails(eventId)
        if(eventRepository.existsByNameIgnoreCaseAndIdNot(request.name,eventId)){
            throw EventAlreadyExistsException(request.name)
        }
        val type = eventTypeRepository.findById(request.typeId!!)
            .orElseThrow { EventTypeNotFoundException(request.typeId) }
        val updated = existing.copy(
            name = request.name,
            date = request.date,
            location = request.location,
            type = type,
            description = request.description
        )
        eventRepository.save(updated)
    }

    fun deleteEvent(eventId: Long){
        val event = viewEventDetails(eventId)
        eventRepository.delete(event)
    }
}
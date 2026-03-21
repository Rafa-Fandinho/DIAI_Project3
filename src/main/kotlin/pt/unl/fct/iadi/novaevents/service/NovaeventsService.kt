package pt.unl.fct.iadi.novaevents.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.novaevents.controller.dto.ClubDetailResponse
import pt.unl.fct.iadi.novaevents.controller.dto.ClubResponse
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.controller.dto.EventDetailResponse
import pt.unl.fct.iadi.novaevents.controller.dto.EventResponse
import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.model.ClubCategory
import pt.unl.fct.iadi.novaevents.model.Event
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

@Service
class NovaeventsService {

    private val clubs = listOf(
        Club(1, "Chess Club", "Play and improve your chess skills", ClubCategory.SPORTS),
        Club(2, "Robotics Club", "The Robotics Club is the place to turn ideas into machines", ClubCategory.TECHNOLOGY),
        Club(3, "Photography Club", "Capture the world through your lens", ClubCategory.ARTS),
        Club(4, "Hiking & Outdoors Club", "Explore nature and adventure", ClubCategory.SPORTS),
        Club(5, "Film Society", "Watch and discuss films", ClubCategory.CULTURAL)
    )

    private val events = mutableListOf(
        Event(1, 1, "Chess Tournament", LocalDate.now().plusDays(5), "Room A", EventType.COMPETITION, "Open tournament"),
        Event(2, 2, "Robotics Workshop", LocalDate.now().plusDays(10), "Lab 1", EventType.WORKSHOP, "Build robots"),
        Event(3, 3, "Photo Walk", LocalDate.now().plusDays(7), null, EventType.SOCIAL, "Outdoor photography"),
    )

    private val eventIdGenerator = AtomicLong(4)

    fun listClubs(): List<ClubResponse> {
        return clubs.map { it.toResponse() }
    }

    fun viewClubDetails(clubId: Long): ClubDetailResponse {
        val club = clubs.find { it.id == clubId }
            ?: throw ClubNotFoundException(clubId)
        val clubEvents = events
            .filter { it.clubId == clubId }
            .map { it.toResponse() }
        return ClubDetailResponse(
            club.id,
            club.name,
            club.description,
            club.category.name,
            clubEvents
        )
    }

    fun listEvents(type: EventType?, clubId: Long?, from: LocalDate?, to: LocalDate?): List<EventResponse> {
        return events
            .asSequence()
            .filter { type == null || it.type == type }
            .filter { clubId == null || it.clubId == clubId }
            .filter { from == null || !it.date.isBefore(from) }
            .filter { to == null || !it.date.isAfter(to) }
            .map { it.toResponse() }
            .toList()
    }

    fun viewEventDetails(clubId: Long, eventId: Long): EventDetailResponse {
        val event = events.find { it.id == eventId && it.clubId == clubId }
            ?: throw EventNotFoundException(eventId)
        val club = clubs.find { it.id == clubId }
            ?: throw ClubNotFoundException(clubId)
        return EventDetailResponse(
            event.id,
            clubId,
            club.name,
            event.name,
            event.date,
            event.location,
            event.type,
            event.description
        )
    }

    fun createEvent(clubId: Long, request: CreateEventRequest): Long{
        if (events.any { it.name.equals(request.name, true) }) {
            throw EventAlreadyExistsException(request.name)
        }
        clubs.find { it.id == clubId }
            ?: throw ClubNotFoundException(clubId)
        val event = Event(
            id = eventIdGenerator.getAndIncrement(),
            clubId = clubId,
            name = request.name,
            date = request.date,
            location = request.location,
            type = request.type,
            description = request.description
        )
        events.add(event)
        return event.id
    }

    fun editEvent(clubId: Long, eventId: Long, request: CreateEventRequest){
        val event = events.find { it.id == eventId && it.clubId == clubId }
            ?: throw EventNotFoundException(eventId)
        if (events.any { it.id != eventId && it.name.equals(request.name, true)}) {
            throw EventAlreadyExistsException(request.name)
        }
        val index = events.indexOf(event)
        events[index] = event.copy(
            name = request.name,
            date = request.date,
            location = request.location,
            type = request.type,
            description = request.description
        )
    }

    fun deleteEvent(clubId: Long, eventId: Long){
        val removed = events.removeIf { it.id == eventId && it.clubId == clubId }
        if(!removed){
            throw EventNotFoundException(eventId)
        }
    }
    private fun Club.toResponse() = ClubResponse(id,name,description,category)

    private fun Event.toResponse() = EventResponse(id,clubId,name,date,location,type,description)
}
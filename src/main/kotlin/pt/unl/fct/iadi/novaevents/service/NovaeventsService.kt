package pt.unl.fct.iadi.novaevents.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.novaevents.controller.dto.ClubDetailResponse
import pt.unl.fct.iadi.novaevents.controller.dto.ClubResponse
import pt.unl.fct.iadi.novaevents.controller.dto.CreateEventRequest
import pt.unl.fct.iadi.novaevents.controller.dto.EventDetailResponse
import pt.unl.fct.iadi.novaevents.controller.dto.EventResponse
import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.model.Event
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

@Service
class NovaeventsService {

    fun listClubs(): List<ClubResponse> {
        //TODO: Complete
    }

    fun viewClubDetails(clubId: Long): List<ClubDetailResponse> {
                 //TODO: Complete
    }

    fun listEvents(type: EventType?, clubId: Long?, from: LocalDate?, to: LocalDate?): List<EventResponse> {
        //TODO: Complete
    }

    fun viewEventDetails(clubId: Long, eventId: Long): List<EventDetailResponse> {
            //TODO: Complete
    }

    fun createEvent(clubId: Long, request: CreateEventRequest): Long{
        //TODO: Complete
    }

    fun editEvent(clubId: Long, eventId: Long, request: CreateEventRequest){
        //TODO: implement
    }

    fun deleteEvent(clubId: Long, eventId: Long){
        //TODO: implement
    }
    private fun Club.toResponse() = ClubResponse(id,name,description,category)

    private fun Event.toResponse() = EventResponse(id,clubId,name,date,location,type,description)
}
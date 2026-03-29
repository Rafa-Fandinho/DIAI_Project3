package pt.unl.fct.iadi.novaevents

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.model.ClubCategory
import pt.unl.fct.iadi.novaevents.model.Event
import pt.unl.fct.iadi.novaevents.model.EventType
import pt.unl.fct.iadi.novaevents.repository.ClubRepository
import pt.unl.fct.iadi.novaevents.repository.EventRepository
import pt.unl.fct.iadi.novaevents.repository.EventTypeRepository
import java.time.LocalDate

@Component
class DataInitializer(
    private val clubRepository: ClubRepository,
    private val eventRepository: EventRepository,
    private val eventTypeRepository : EventTypeRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        if(eventTypeRepository.count() > 0) return

        // Event Types
        val workshop = eventTypeRepository.save(EventType(name = "WORKSHOP"))
        val talk = eventTypeRepository.save(EventType(name = "TALK"))
        val meeting = eventTypeRepository.save(EventType(name = "MEETING"))
        val social = eventTypeRepository.save(EventType(name = "SOCIAL"))
        val other = eventTypeRepository.save(EventType(name = "OTHER"))
        val competition = eventTypeRepository.save(EventType(name = "COMPETITION"))

        // Clubs
        val chess = clubRepository.save(Club(name = "Chess Club", description = "Chess stuff", category = ClubCategory.SPORTS))
        val robotics = clubRepository.save(Club(name = "Robotics Club", description = "Robotics stuff", category = ClubCategory.ACADEMIC))
        val photography = clubRepository.save(Club(name = "Photography Club", description = "Photography stuff", category = ClubCategory.CULTURAL))
        val outdoors = clubRepository.save(Club(name = "Hiking & Outdoors Club", description = "Outdoors stuff", category = ClubCategory.SOCIAL))
        val film = clubRepository.save(Club(name = "Film Society", description = "Films stuff", category = ClubCategory.ARTS))


        // Events (15)
        val events = listOf(
            Event(club = chess, name = "Beginner's Chess Workshop", date = LocalDate.of(2026,3,10), location = "Room A101", type = workshop, description = "Chess class"),
            Event(club = chess, name = "Spring Chess Tournament", date = LocalDate.of(2026,4,5), location = "Main Hall", type = competition, description = "Preguiça"),
            Event(club = chess, name = "Advanced Openings Talk", date = LocalDate.of(2026,5,20), location = "Room A101", type = talk, description = "Preguiça"),
            Event(club = robotics, name = "Arduino Intro Workshop", date = LocalDate.of(2026,3,15), location = "Engineering Lab 2", type = workshop, description = "Preguiça"),
            Event(club = robotics, name = "RoboCup Preparation Meeting", date = LocalDate.of(2026,3,28), location = "Engineering Lab 1", type = meeting, description = "Preguiça"),
            Event(club = robotics, name = "Sensor Integration Talk", date = LocalDate.of(2026,4,22), location = "Auditorium B", type = talk, description = "Preguiça"),
            Event(club = robotics, name = "Regional Robotics Competition", date = LocalDate.of(2026,6,1), location = "Sports Hall", type = competition, description = "Preguiça"),
            Event(club = photography, name = "Night Photography Workshop", date = LocalDate.of(2026,3,22), location = "Campus Rooftop", type = workshop, description = "Preguiça"),
            Event(club = photography, name = "Portrait Photography Talk", date = LocalDate.of(2026,4,14), location = "Arts Studio 3", type = talk, description = "Preguiça"),
            Event(club = photography, name = "Photo Walk & Social", date = LocalDate.of(2026,5,9), location = "Main Entrance", type = social, description = "Preguiça"),
            Event(club = outdoors, name = "Serra da Arrábida Hike", date = LocalDate.of(2026,3,29), location = "Bus Stop Central", type = other, description = "Preguiça"),
            Event(club = outdoors, name = "Trail Safety Workshop", date = LocalDate.of(2026,4,8), location = "Room C205", type = workshop, description = "Preguiça"),
            Event(club = outdoors, name = "Spring Camping Trip", date = LocalDate.of(2026,5,15), location = "Bus Stop Central", type = social, description = "Preguiça"),
            Event(club = film, name = "Kubrick Retrospective Screening", date = LocalDate.of(2026,3,18), location = "Cinema Room", type = social, description = "Preguiça"),
            Event(club = film, name = "Screenwriting Workshop", date = LocalDate.of(2026,4,30), location = "Arts Studio 1", type = workshop, description = "Preguiça")
        )

        eventRepository.saveAll(events)
    }
}
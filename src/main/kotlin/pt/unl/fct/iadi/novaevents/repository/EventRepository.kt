package pt.unl.fct.iadi.novaevents.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.unl.fct.iadi.novaevents.model.Event
import pt.unl.fct.iadi.novaevents.model.EventType
import java.time.LocalDate

interface EventRepository : JpaRepository<Event, Long> {
    fun existsByNameIgnoreCase(name: String): Boolean

    fun existsByNameIgnoreCaseAndIdNot(name: String, id: Long): Boolean

    fun findByClubId(clubId: Long): List<Event>

    fun findByType(type: EventType): List<Event>

    fun findByClubIdAndType(clubId: Long, type: EventType): List<Event>

    fun findByDateBetween(from: LocalDate, to: LocalDate): List<Event>

    fun findByClubIdAndTypeAndDateBetween(clubId: Long, type: EventType, from: LocalDate, to: LocalDate): List<Event>
}
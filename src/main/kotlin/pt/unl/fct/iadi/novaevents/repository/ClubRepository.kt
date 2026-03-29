package pt.unl.fct.iadi.novaevents.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pt.unl.fct.iadi.novaevents.model.Club

interface ClubRepository : JpaRepository<Club, Long> {
    @Query("""
        SELECT COUNT(e)
        FROM Event e
        WHERE e.club.id = :clubId
    """)
    fun countEventsPerClub(clubId: Long): Int
}
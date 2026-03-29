package pt.unl.fct.iadi.novaevents.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.novaevents.controller.dto.ClubResponse
import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.repository.ClubRepository

@Service
class ClubService(
    private val clubRepository: ClubRepository,
) {
    fun findAll(): List<Club> = clubRepository.findAll()

    fun findById(clubId: Long): Club = clubRepository.findById(clubId)
        .orElseThrow { ClubNotFoundException(clubId) }
    fun findAllWithCounts(): List<ClubResponse> {
        return clubRepository.findAll()
            .map { club ->
                val count = clubRepository.countEventsPerClub(club.id!!)
                ClubResponse(
                    id = club.id!!,
                    name = club.name,
                    category = club.category,
                    description = club.description,
                    eventCount = count
                )

            }
    }
}
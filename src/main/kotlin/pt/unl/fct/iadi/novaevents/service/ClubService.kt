package pt.unl.fct.iadi.novaevents.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.repository.ClubRepository

@Service
class ClubService(
    private val clubRepository: ClubRepository
) {
    fun findAll(): List<Club> = clubRepository.findAll()

    fun findById(clubId: Long): Club = clubRepository.findById(clubId)
        .orElseThrow { ClubNotFoundException(clubId) }
    fun findAllWithCounts(): List<Pair<Club,Long>> = clubRepository.findClubsWithEventCount()
        .map { Pair(it[0] as Club, it[1] as Long) }
}
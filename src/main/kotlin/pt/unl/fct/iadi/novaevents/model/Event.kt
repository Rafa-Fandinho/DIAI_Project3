package pt.unl.fct.iadi.novaevents.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
data class Event(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    val club : Club,
    val name : String,
    val date : LocalDate,
    val location : String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    val type : EventType,
    val description : String?
)

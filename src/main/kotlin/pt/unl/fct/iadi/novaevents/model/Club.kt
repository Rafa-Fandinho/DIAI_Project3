package pt.unl.fct.iadi.novaevents.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Size

@Entity
data class Club(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
    val name: String,
    @field:Size(max = 2000)
    val description: String,
    @Enumerated(EnumType.STRING)
    val category: ClubCategory
)

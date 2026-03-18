package pt.unl.fct.iadi.novaevents.model

import java.util.Date

data class Event(
    val clubID : Long,  //Must add reference to the club
    val name : String,   //Must add restrictions too
    val date : Date,
    val location : String?,
    val type : EventType,
    val description : String?
){
    val id : Long get() = hashCode().toLong()
}

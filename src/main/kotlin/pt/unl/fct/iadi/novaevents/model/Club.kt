package pt.unl.fct.iadi.novaevents.model

data class Club(
    val name: String,
    val description: String,
    val category: ClubCategory
){
    val id : Long get() = hashCode().toLong()
}

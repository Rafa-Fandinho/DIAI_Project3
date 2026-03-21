package pt.unl.fct.iadi.novaevents.service

class EventNotFoundException(id: Long): RuntimeException(
    "Event with id '$id' not found"
)
package pt.unl.fct.iadi.novaevents.service

class EventTypeNotFoundException(id: Long?): RuntimeException(
    "Event type with id '$id' not found"
)
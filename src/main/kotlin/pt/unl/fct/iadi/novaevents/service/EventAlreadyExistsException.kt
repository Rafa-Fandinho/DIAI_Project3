package pt.unl.fct.iadi.novaevents.service

class EventAlreadyExistsException(id: Long): RuntimeException(
    "Event with id '$id' is already exists"
)
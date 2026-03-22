package pt.unl.fct.iadi.novaevents.service

class EventAlreadyExistsException(name: String): RuntimeException(
    "An event with this name already exists"
)
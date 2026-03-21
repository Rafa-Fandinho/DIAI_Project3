package pt.unl.fct.iadi.novaevents.service

class EventAlreadyExistsException(name: String): RuntimeException(
    "Event '$name' already exists"
)
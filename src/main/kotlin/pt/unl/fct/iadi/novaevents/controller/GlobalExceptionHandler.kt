package pt.unl.fct.iadi.novaevents.controller

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import pt.unl.fct.iadi.novaevents.service.ClubNotFoundException
import pt.unl.fct.iadi.novaevents.service.EventAlreadyExistsException
import pt.unl.fct.iadi.novaevents.service.EventNotFoundException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ClubNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleClubNotFound(ex: ClubNotFoundException, model: Model): String {
        model.addAttribute("error", ex.message)
        return "error/404"
    }

    @ExceptionHandler(EventAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEventAlreadyExists(ex: EventAlreadyExistsException, model: Model): String {
        model.addAttribute("error", ex.message)
        return "error/400"
    }

    @ExceptionHandler(EventNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEventNotFound(ex: EventNotFoundException, model: Model): String {
        model.addAttribute("error", ex.message)
        return "error/404"
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneric(ex: Exception, model: Model): String {
        model.addAttribute("error", ex.message)
        return "error/500"
    }
}
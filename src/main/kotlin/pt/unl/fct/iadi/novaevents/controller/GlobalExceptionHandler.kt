package pt.unl.fct.iadi.novaevents.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pt.unl.fct.iadi.novaevents.controller.dto.ErrorResponse
import pt.unl.fct.iadi.novaevents.service.ClubNotFoundException
import pt.unl.fct.iadi.novaevents.service.EventAlreadyExistsException
import pt.unl.fct.iadi.novaevents.service.EventNotFoundException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ClubNotFoundException::class)
    fun handleBookNotFoundException(ex: ClubNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            error = "NOT_FOUND",
            message = ex.message ?: "Club not found",
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(EventAlreadyExistsException::class)
    fun handleBookAlreadyExistsException(ex: EventAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            error = "CONFLICT",
            message = ex.message ?: "Event already exists"
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    @ExceptionHandler(EventNotFoundException::class)
    fun handleReviewNotFoundException(ex: EventNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            error = "NOT_FOUND",
            message = ex.message ?: "Event not found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            error = ex.message ?: "INTERNAL_SERVER_ERROR",
            message = ex.message ?: "Unexpected error"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }
}
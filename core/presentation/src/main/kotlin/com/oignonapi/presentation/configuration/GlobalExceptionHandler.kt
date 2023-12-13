package com.oignonapi.presentation.configuration

import com.oignonapi.domain.exceptions.NotFoundException
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Cette classe permet de centraliser la gestion des exceptions. Toutes les exceptions
 * sont interceptées ici. Important : les exceptions doivent étendre directement ou
 * indirectement `RuntimeException`. Cela permet d'éviter de se retrouver avec une
 * `UndeclaredThrowableException` qui serait interceptée par la méthode `handleThrowable`
 * qui renvoie systématiquement une erreur 503 => https://stackoverflow.com/a/5490372
 */
@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        notFoundException: NotFoundException,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(NOT_FOUND)
            .body(notFoundException.message)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        illegalArgumentException: IllegalArgumentException,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(illegalArgumentException.message)
    }

    // Exceptions non gérées
    @ExceptionHandler(Throwable::class)
    fun handleThrowable(
        throwable: Throwable,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            // Par mesure de précaution, on ne retourne pas le message
            // d'erreur parce qu'il peut contenir des informations sensibles
            .body(INTERNAL_SERVER_ERROR)
    }
}

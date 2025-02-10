package com.apress.myretro.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ControllerAdvice
class RetroBoardResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [CardNotFoundException::class, RetroBoardNotFoundException::class])
    protected fun handleNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val response: Map<String, Any> = mapOf(
            "msg" to "There is an error",
            "code" to HttpStatus.NOT_FOUND.value(),
            "time" to LocalDateTime.now().format(DateTimeFormatter.
            ofPattern("yyyy-mm-dd HH:mm:ss")),
            "errors" to mapOf(
                "msg" to ex.message)
        )
        return handleExceptionInternal(
            ex, response,
            HttpHeaders(), HttpStatus.NOT_FOUND, request
        )
    }
}

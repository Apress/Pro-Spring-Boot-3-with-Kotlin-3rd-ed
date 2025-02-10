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
    protected fun handleNotFound(
        ex: RuntimeException, request: WebRequest?
    ): ResponseEntity<Any>? {
        val response: MutableMap<String, Any> = HashMap()
        response["msg"] = "There is an error"
        response["code"] = HttpStatus.NOT_FOUND.value()
        response["time"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"))
        val errors: MutableMap<String, String?> = HashMap()
        errors["msg"] = ex.message
        response["errors"] = errors
        return handleExceptionInternal(
            ex, response,
            HttpHeaders(), HttpStatus.NOT_FOUND, request!!
        )
    }
}

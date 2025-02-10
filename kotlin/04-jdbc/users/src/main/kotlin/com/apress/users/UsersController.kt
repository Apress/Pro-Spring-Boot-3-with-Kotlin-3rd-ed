package com.apress.users

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/users")
class UsersController {
    @Autowired
    private lateinit var userRepository: SimpleRepository<User, Int>

    @get:GetMapping
    val all: ResponseEntity<Iterable<User>>
        get() = ResponseEntity.ok(userRepository.findAll())

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Int): ResponseEntity<User> =
        ResponseEntity.ofNullable(userRepository.findById(id))

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun save(@RequestBody user: @Valid User): ResponseEntity<User> {
        val result = userRepository.save(user)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user)
            .toUri()
        return ResponseEntity.created(location).body(
            userRepository.findById(result.id!!)
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        userRepository.deleteById(id)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> =
        ex.bindingResult.allErrors.associate { error: ObjectError ->
            (error as FieldError).field to (error.getDefaultMessage() ?: "undef")
        }.toMutableMap().apply {
            this["time"] = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): Map<String, Any> =
        mapOf(
            "code" to HttpStatus.BAD_REQUEST.value(),
            "message" to (ex.message?:"undef"),
            "time" to LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
}

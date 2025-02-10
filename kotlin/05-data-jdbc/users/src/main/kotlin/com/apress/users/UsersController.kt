package com.apress.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/users")
class UsersController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @get:GetMapping
    val all: ResponseEntity<Iterable<User>>
        get() = ResponseEntity.ok(userRepository.findAll())

    @GetMapping("/{email}")
    @Throws(Throwable::class)
    fun findUserByEmail(@PathVariable email: String): ResponseEntity<User> =
        ResponseEntity.ofNullable(userRepository.findByEmail(email))

    @RequestMapping(method = [RequestMethod.POST, RequestMethod.PUT])
    fun save(@RequestBody user: User): ResponseEntity<User> {
        userRepository.save(user)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{email}")
            .buildAndExpand(user.id)
            .toUri()
        return ResponseEntity.created(location).body(userRepository.findByEmail(user.email!!))
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable email: String) {
        userRepository.deleteByEmail(email)
    }

    //Example custom Query
    @GetMapping("/gravatar/{email}")
    @Throws(Throwable::class)
    fun getGravatarByEmail(@PathVariable email: String): ResponseEntity<String> =
        ResponseEntity.ok(userRepository.getGravatarByEmail(email))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> =
        ex.bindingResult.allErrors.associate { error: ObjectError ->
            (error as FieldError).field to (error.getDefaultMessage() ?: "undef")
        }.toMutableMap().apply {
            this["time"] = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
}

package com.apress.users

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class UsersController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @QueryMapping
    fun users(): Iterable<User> =
        userRepository.findAll()

    @QueryMapping
    @Throws(Throwable::class)
    fun user(@Argument email: String): User =
        userRepository.findById(email).orElseThrow { RuntimeException("User not found") }!!

    @MutationMapping
    fun createUser(@Argument @Valid user: User): User {
        user.gravatarUrl = UserGravatar.getGravatarUrlFromEmail(user.email!!)
        return userRepository.save(user)
    }

    @MutationMapping
    fun updateUser(@Argument  @Valid user: User): User {
        val userToUpdate = userRepository.findById(user.email!!)
            .orElseThrow { RuntimeException("User not found") }!!.apply {
                name = user.name
                password = user.password
                userRole = user.userRole
                active = user.active
            }
        return userRepository.save(userToUpdate)
    }

    @MutationMapping
    fun deleteUser(@Argument email: String): Boolean {
        userRepository.deleteById(email)
        return true
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        response["msg"] = "There is an error"
        response["code"] = HttpStatus.BAD_REQUEST.value()
        response["time"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.allErrors.forEach{ error: ObjectError ->
            val fieldName: String = (error as FieldError).field
            val errorMessage: String = error.getDefaultMessage() ?: "undef"
            errors[fieldName] = errorMessage
        }
        response["errors"] = errors
        return response
    }
}

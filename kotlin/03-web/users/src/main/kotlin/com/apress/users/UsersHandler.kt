package com.apress.users

import jakarta.servlet.ServletException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.validation.DataBinder
import org.springframework.validation.Validator
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class UsersHandler {
    @Autowired
    private lateinit var userRepository: Repository<User, String>
    @Autowired
    private lateinit var validator: Validator

    fun findAll(request: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userRepository.findAll())
    }

    fun findUserByEmail(request: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userRepository.findById(request.pathVariable("email"))?:"{}")
    }

    @Throws(ServletException::class, IOException::class)
    fun save(request: ServerRequest): ServerResponse {
        val user = request.body(User::class.java)
        val bindingResult = validate(user)
        if (bindingResult.hasErrors()) {
            return prepareErrorResponse(bindingResult)
        }
        userRepository.save(user)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{email}")
            .buildAndExpand(user.email)
            .toUri()
        return ServerResponse.created(location).body(user)
    }

    fun deleteByEmail(request: ServerRequest): ServerResponse {
        userRepository.deleteById(request.pathVariable("email"))
        return ServerResponse.noContent().build()
    }

    private fun validate(user: User): BindingResult {
        val binder = DataBinder(user).apply {
            addValidators(this@UsersHandler.validator)
        }
        binder.validate()
        return binder.bindingResult
    }

    private fun prepareErrorResponse(bindingResult: BindingResult): ServerResponse {
        val response: MutableMap<String, Any> = mutableMapOf(
            "msg" to "There is an error",
            "code" to HttpStatus.BAD_REQUEST.value(),
            "time" to LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            "errors" to bindingResult.fieldErrors.associate { fieldError ->
                fieldError.field to fieldError.defaultMessage
            }
        )
        return ServerResponse.badRequest().body(response)
    }
}

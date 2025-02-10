package com.apress.myretro.web

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
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
import java.util.*

@Controller
class RetroBoardController {
    @Autowired
    private lateinit var retroBoardService: RetroBoardService

    @QueryMapping
    fun retros(): Iterable<RetroBoard> =
        retroBoardService.findAll()

    @MutationMapping
    fun createRetro(@Argument name: String?): RetroBoard {
        return retroBoardService.save(RetroBoard(id = UUID.randomUUID(), name=name))
    }

    @QueryMapping
    fun retro(@Argument retroId: UUID): RetroBoard =
        retroBoardService.findById(retroId)

    @QueryMapping
    fun cards(@Argument retroId: UUID): Iterable<Card> =
        retroBoardService.findAllCardsFromRetroBoard(retroId)

    @MutationMapping
    fun createCard(@Argument retroId: UUID, @Argument @Valid card: Card): Card {
        return retroBoardService.addCardToRetroBoard(retroId, card)
    }

    @QueryMapping
    fun card(@Argument cardId: UUID): Card =
        retroBoardService.findCardByUUID(cardId)

    @MutationMapping
    fun updateCard(@Argument cardId: UUID, @Argument @Valid card: Card): Card {
        val result: Card = retroBoardService.findCardByUUID(cardId)
        result.comment = card.comment
        return retroBoardService.saveCard(result)
    }

    @MutationMapping
    fun deleteCard(@Argument cardId: UUID): Boolean {
        retroBoardService.removeCardByUUID(cardId)
        return true
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, Any> {
        val response: MutableMap<String, Any> = mutableMapOf()
        response["msg"] = "There is an error"
        response["code"] = HttpStatus.BAD_REQUEST.value()
        response["time"] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach{ error: ObjectError ->
            val fieldName: String = (error as FieldError).field
            val errorMessage: String = error.getDefaultMessage() ?: "undef"
            errors[fieldName] = errorMessage
        }
        response["errors"] = errors
        return response
    }
}

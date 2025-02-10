package com.apress.myretro.web

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import com.apress.myretro.service.RetroBoardService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.Serializable
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("/retros")
class RetroBoardController {
    private lateinit var retroBoardService: RetroBoardService

    @get:GetMapping
    val allRetroBoards: ResponseEntity<Iterable<RetroBoard>>
        get() = ResponseEntity.ok(retroBoardService.findAll())

    @PostMapping
    fun saveRetroBoard(@RequestBody retroBoard: @Valid RetroBoard): ResponseEntity<RetroBoard> {
        val result: RetroBoard? = retroBoardService.save(retroBoard)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{uuid}")
            .buildAndExpand(result!!.id.toString())
            .toUri()
        return ResponseEntity.created(location).body<RetroBoard>(result)
    }

    @GetMapping("/{uuid}")
    fun findRetroBoardById(@PathVariable uuid: UUID): ResponseEntity<RetroBoard> {
        return ResponseEntity.ok<RetroBoard>(retroBoardService.findById(uuid))
    }

    @GetMapping("/{uuid}/cards")
    fun getAllCardsFromBoard(@PathVariable uuid: UUID): ResponseEntity<Iterable<Card>> {
        return ResponseEntity.ok(retroBoardService.findAllCardsFromRetroBoard(uuid))
    }

    @PutMapping("/{uuid}/cards")
    fun addCardToRetroBoard(@PathVariable uuid: UUID, @RequestBody card: @Valid Card): ResponseEntity<Card> {
        val result: Card = retroBoardService.addCardToRetroBoard(uuid, card)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{uuid}/cards/{uuidCard}")
            .buildAndExpand(uuid.toString(), result.id.toString())
            .toUri()
        return ResponseEntity.created(location).body<Card>(result)
    }

    @GetMapping("/{uuid}/cards/{uuidCard}")
    fun getCardFromRetroBoard(@PathVariable uuid: UUID, @PathVariable uuidCard: UUID?): ResponseEntity<Card> {
        return ResponseEntity.ok<Card>(retroBoardService.findCardByUUIDFromRetroBoard(uuid, uuidCard))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    fun deleteCardFromRetroBoard(@PathVariable uuid: UUID, @PathVariable uuidCard: UUID) {
        retroBoardService.removeCardFromRetroBoard(uuid, uuidCard)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, Any> {
        val response:MutableMap<String,Any> = mutableMapOf(
          "msg" to "There is an error",
          "code" to HttpStatus.BAD_REQUEST.value(),
          "time" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.allErrors.forEach{ error: ObjectError ->
            val fieldName: String = (error as FieldError).field
            val errorMessage: String = error.getDefaultMessage()!!
            errors[fieldName] = errorMessage
        }
        response["errors"] = errors
        return response
    }
}

package com.apress.myretro.board

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document
data class RetroBoard(
    @Id
    @get:NotNull
    var id:  UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name: String? = null,

    var cards: MutableList<Card>? = null
){
    fun addCard(card: Card) {
        cards = cards ?: mutableListOf()
        cards!!.add(card)
    }

    fun addCards(cards: List<Card>) {
        this.cards = this.cards ?: mutableListOf()
        this.cards!!.addAll(cards)
    }

    fun removeCard(cardId: UUID) {
        if (cards == null) return
        cards!!.removeIf { card: Card -> card.id == cardId }
    }
}

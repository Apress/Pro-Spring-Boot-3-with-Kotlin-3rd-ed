package com.apress.myretro.board

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class RetroBoard(
    @Id var id: UUID? = null,
    var name: String? = null,
    var cards: MutableList<Card>? = null
){
    fun addCard(card: Card) = also { cards = cards ?: mutableListOf() }.run {
        cards!!.add(card)
    }

    fun addCards(cards: List<Card>) = also { this.cards = this.cards ?: mutableListOf() }.run {
        this.cards!!.addAll(cards)
    }
}

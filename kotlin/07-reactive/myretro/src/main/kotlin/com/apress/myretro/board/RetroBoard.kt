package com.apress.myretro.board

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class RetroBoard(
    @Id
    var id: UUID? = null,

    var name: String? = null,

    var cards: MutableList<Card>? = null
){
    fun addCard(card: Card) = ensureCardsExist().add(card)

    fun addCards(cards: Collection<Card>) = ensureCardsExist().addAll(cards)

    private fun ensureCardsExist() = this.run { cards = cards ?: mutableListOf(); cards!! }
}

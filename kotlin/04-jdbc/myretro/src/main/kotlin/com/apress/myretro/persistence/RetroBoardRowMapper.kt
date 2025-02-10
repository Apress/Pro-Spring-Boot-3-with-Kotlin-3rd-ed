package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import com.apress.myretro.board.CardType
import com.apress.myretro.board.RetroBoard
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class RetroBoardRowMapper : RowMapper<RetroBoard> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): RetroBoard {
        val retroBoard = RetroBoard(
            id = UUID.fromString(rs.getString("id")),
            name = rs.getString("name"))
        val cards: MutableMap<UUID, Card> = mutableMapOf()
        do {
            val card = Card(
                id = UUID.fromString(rs.getString("card_id")),
                comment = rs.getString("comment"),
                cardType = CardType.valueOf(rs.getString("card_type")),
                retroBoardId = retroBoard.id
            )
            cards[card.id!!] = card
        } while (rs.next() && retroBoard.id == UUID.fromString(rs.getString("id")))
        retroBoard.cards = cards
        return retroBoard
    }
}

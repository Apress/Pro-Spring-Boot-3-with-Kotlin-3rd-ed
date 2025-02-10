package com.apress.myretro.persistence

import com.apress.myretro.board.Card
import com.apress.myretro.board.RetroBoard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Types
import java.util.*

@Repository
class RetroBoardRepository : SimpleRepository<RetroBoard, UUID> {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun findById(uuid: UUID): RetroBoard? {
        val sql = """
                SELECT r.ID AS id, r.NAME, c.ID AS card_id, c.CARD_TYPE AS card_type, c.COMMENT AS comment
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                WHERE r.ID = ?
                """.trimIndent()
        val results: List<RetroBoard> =
            jdbcTemplate.query(sql, arrayOf<Any>(uuid), intArrayOf(Types.OTHER), RetroBoardRowMapper())
        return if (results.isEmpty()) null else results[0]
    }

    override fun findAll(): Iterable<RetroBoard> {
        val sql = """
                SELECT r.ID AS id, r.NAME, c.ID AS card_id, c.CARD_TYPE, c.COMMENT
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                """.trimIndent()
        return jdbcTemplate.query<RetroBoard>(sql, RetroBoardRowMapper())
    }

    @Transactional
    override fun save(retroBoard: RetroBoard): RetroBoard {
        retroBoard.id = retroBoard.id ?: UUID.randomUUID()
        val sql = "INSERT INTO RETRO_BOARD (ID, NAME) VALUES (?, ?)"
        jdbcTemplate.update(sql, retroBoard.id, retroBoard.name)
        val cards: MutableMap<UUID, Card> = retroBoard.cards.mapValues { me ->
            me.value.let { it.retroBoardId = retroBoard.id; saveCard(it) }
        }.toMutableMap()
        retroBoard.cards = cards
        return retroBoard
    }

    @Transactional
    override fun deleteById(uuid: UUID) {
        var sql = "DELETE FROM CARD WHERE RETRO_BOARD_ID = ?"
        jdbcTemplate.update(sql, uuid)
        sql = "DELETE FROM RETRO_BOARD WHERE ID = ?"
        jdbcTemplate.update(sql, uuid)
    }

    private fun saveCard(card: Card): Card {
        card.id = card.id ?: UUID.randomUUID()
        val sql = "INSERT INTO CARD (ID, CARD_TYPE, COMMENT, RETRO_BOARD_ID) VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(sql,
            card.id, card.cardType!!.name, card.comment, card.retroBoardId)
        return card
    }
}

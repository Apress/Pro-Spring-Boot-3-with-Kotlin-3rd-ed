package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
data class Card(
    @Id
    var id: UUID? = null,

    @get:NotBlank
    var comment: String?,

    @get:NotNull
    var cardType: CardType?,

    @ManyToOne
    @JoinColumn(name = "retro_board_id")
    @JsonIgnore
    var retroBoard: RetroBoard? = null
){
    @PrePersist
    private fun prePersist() {
        id = id ?: UUID.randomUUID()
    }
}

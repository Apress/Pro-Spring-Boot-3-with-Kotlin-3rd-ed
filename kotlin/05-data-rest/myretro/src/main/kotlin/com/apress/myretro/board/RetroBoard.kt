package com.apress.myretro.board

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
data class RetroBoard(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    var id: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name: String? = null,

    @OneToMany(mappedBy = "retroBoard")
    var cards: MutableList<Card> = mutableListOf()
)

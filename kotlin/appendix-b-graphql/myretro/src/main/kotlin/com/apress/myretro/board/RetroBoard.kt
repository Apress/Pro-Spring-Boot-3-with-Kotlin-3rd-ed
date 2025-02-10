package com.apress.myretro.board

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
@Table(name = "retro_board")
data class RetroBoard(
    @Id
    var id: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    var name: String? = null,

    @OneToMany(mappedBy = "retro_board")
    var cards: List<Card>? = null
){
    @PrePersist
    private fun prePersist() {
        id = id ?: UUID.randomUUID()
    }
}

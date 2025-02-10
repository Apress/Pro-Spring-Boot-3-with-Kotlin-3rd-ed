package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

@jakarta.persistence.Entity
data class Card(
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    @Id
    @get:NotNull
    var cardId:  java.util.UUID? = null,

    @get:NotBlank
    var comment: String? = null,

    @get:NotNull
    var cardType:  CardType? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = true, updatable = false)
    @get:NotNull
    var created: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = true, updatable = true)
    @get:NotNull
    var modified: LocalDateTime? = null
){
    @PrePersist
    fun onPrePersist() {
        created = LocalDateTime.now()
        modified = LocalDateTime.now()
    }

    @PreUpdate
    fun onPreUpdate() {
        modified = LocalDateTime.now()
    }
}

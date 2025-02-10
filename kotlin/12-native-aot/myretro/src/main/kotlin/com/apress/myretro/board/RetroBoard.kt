package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*

@Entity
open class RetroBoard {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    @Id
    open var retroBoardId: UUID? = null

    @get:NotBlank(message = "A name must be provided")
    @get:NotNull
    open var name: String? = null

    @OneToMany(cascade = [CascadeType.ALL], targetEntity = Card::class)
    @JoinColumn(name = "retroBoardId")
    open var cards: List<Card>? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = true, updatable = false)
    @get:NotNull
    open var created: LocalDateTime? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(insertable = true, updatable = true)
    @get:NotNull
    open var modified: LocalDateTime? = null

    @PrePersist
    open fun onPrePersist() {
        created = LocalDateTime.now()
        modified = LocalDateTime.now()
    }

    @PreUpdate
    open fun onPreUpdate() {
        modified = LocalDateTime.now()
    }
}

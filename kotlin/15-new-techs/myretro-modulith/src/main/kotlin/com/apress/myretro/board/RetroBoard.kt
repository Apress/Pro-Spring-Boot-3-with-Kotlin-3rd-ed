package com.apress.myretro.board

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*

@Entity
data class RetroBoard(
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    @Id
    var retroBoardId: UUID? = null,

    @get:NotBlank(message = "A name must be provided")
    @get:NotNull
    var name:  String? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "retroBoardId")
    var cards: List<Card>? = null,

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

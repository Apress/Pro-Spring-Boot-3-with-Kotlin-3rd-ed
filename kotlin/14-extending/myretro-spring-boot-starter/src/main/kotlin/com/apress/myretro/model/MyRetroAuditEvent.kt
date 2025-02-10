package com.apress.myretro.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class MyRetroAuditEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var timestamp: LocalDateTime = LocalDateTime.now(),

    var interceptor: String? = null,
    var method: String? = null,
    var args: String? = null,
    var result: String? = null,
    var message: String? = null
)

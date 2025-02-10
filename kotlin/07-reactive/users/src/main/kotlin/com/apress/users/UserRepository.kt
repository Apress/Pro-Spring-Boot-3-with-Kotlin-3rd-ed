package com.apress.users

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface UserRepository : ReactiveCrudRepository<User, UUID> {
    fun findByEmail(email: String): Mono<User>
    fun deleteByEmail(email: String): Mono<Void>
}

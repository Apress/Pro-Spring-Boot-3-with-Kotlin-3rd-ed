package com.apress.users

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?

    @Transactional
    fun deleteByEmail(email: String?)
}

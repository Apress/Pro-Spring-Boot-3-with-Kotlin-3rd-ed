package com.apress.users

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    fun findByEmail(email: String): User?
}

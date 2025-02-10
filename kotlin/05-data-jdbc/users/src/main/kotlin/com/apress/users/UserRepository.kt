package com.apress.users

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun deleteByEmail(email: String)

    // Example of a custom query
    @Query("SELECT GRAVATAR_URL FROM USERS WHERE EMAIL = :email")
    fun getGravatarByEmail(@Param("email") email: String): String?
}

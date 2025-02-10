package com.apress.users

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("PEOPLE")
@JvmRecord
data class User(
    @Id
    val id: UUID?,

    @get:NotBlank(message = "Email can not be empty")
    val email:  String?,

    @get:NotBlank(message = "Name can not be empty")
    val name:  String?,

    val gravatarUrl: String?,

    @get:NotBlank(message = "Password can not be empty")
    val password:  String?,

    val userRole: Collection<UserRole>,
    val active: Boolean
) {
    fun withGravatarUrl(email: String): User {
        val url = UserGravatar.getGravatarUrlFromEmail(email)
        return User(UUID.randomUUID(), email, name, url, password, userRole, active)
    }
}

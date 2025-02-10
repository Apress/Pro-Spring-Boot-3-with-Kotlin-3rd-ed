package com.apress.users

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


@Entity(name = "USERS")
data class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    var email:  String? = null,

    @get:NotBlank(message = "Name can not be empty")
    var name: String? = null,

    var gravatarUrl: String? = null,

    @get:Pattern(
        message = "Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character",
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    )
    var password:String? = null,

    var userRole: List<UserRole>? = null,

    var active:Boolean = false
){
    @PrePersist
    private fun prePersist() {
        gravatarUrl = gravatarUrl ?: UserGravatar.getGravatarUrlFromEmail(email!!)
        userRole = userRole ?: listOf(UserRole.INFO)
    }
}

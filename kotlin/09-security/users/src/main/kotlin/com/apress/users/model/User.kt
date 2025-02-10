package com.apress.users.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity(name = "PEOPLE")
data class User(
    @Id
    @get:NotBlank(message = "Email can not be empty")
    @get:NotNull(message = "Email can not be null")
    var email:  String? = null,

    @get:NotBlank(message = "Name can not be empty")
    @get:NotNull(message = "Name can not be null")
    var name:  String? = null,

    var gravatarUrl: String? = null,

    @get:NotBlank(message = "Password can not be empty")
    @get:NotNull(message = "Password can not be null")
    @get:Size(
        min = 8,
        message = "Password must be at least 8 characters long"
    )
    var password: String? = null,

    var userRole: MutableList<UserRole>? = null,
    var active:Boolean = false
){

    fun setUserRoleArr(vararg userRoles: UserRole) {
        userRole = mutableListOf(*userRoles)
    }

    @PrePersist
    fun setGravatarUrlFromEmail() {
        gravatarUrl = UserGravatar.getGravatarUrlFromEmail(email!!)
    }

    @PreUpdate
    fun updateGravatarUrlFromEmail() {
        gravatarUrl = UserGravatar.getGravatarUrlFromEmail(email!!)
    }
}

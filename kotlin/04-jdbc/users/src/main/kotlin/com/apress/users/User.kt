package com.apress.users

import java.util.regex.Pattern

class User(
    var id: Int? = null,
    var email: String,
    var name: String,
    var password: String,
    var active: Boolean,
    var gravatarUrl: String? = null,
    var userRole: MutableList<UserRole>? = mutableListOf()
) {
    fun withId(id: Int): User {
        return User(id, email, name, password, active, gravatarUrl, userRole)
    }

    init {
        var pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
        var matcher = pattern.matcher(password)
        require(matcher.matches()) { "Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character" }
        pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        matcher = pattern.matcher(email)
        require(matcher.matches()) { "Email must be a valid email address" }
        gravatarUrl = gravatarUrl?:UserGravatar.getGravatarUrlFromEmail(email)
        userRole = userRole?: mutableListOf(UserRole.INFO)
    }
}

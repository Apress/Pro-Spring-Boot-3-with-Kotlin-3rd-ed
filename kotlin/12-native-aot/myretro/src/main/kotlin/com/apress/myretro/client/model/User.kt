package com.apress.myretro.client.model


open class User @JvmOverloads constructor(
    open var email: String? = null,
    open var name: String? = null,
    open var gravatarUrl: String? = null,
    open var password: String? = null,
    open var userRole: Collection<UserRole>? = null,
    open var active: Boolean? = null
)

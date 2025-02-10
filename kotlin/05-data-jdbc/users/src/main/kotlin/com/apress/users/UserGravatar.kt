package com.apress.users

import java.security.MessageDigest

object UserGravatar {
    @OptIn(ExperimentalStdlibApi::class)
    fun getGravatarUrlFromEmail(email: String) =
        String.format("https://www.gravatar.com/avatar/%s?d=wavatar", md5Hex(email))

    @kotlin.ExperimentalStdlibApi
    private fun md5Hex(message: String) =
        MessageDigest.getInstance("MD5")
            .digest(message.toByteArray(charset("CP1252"))).toHexString()
}

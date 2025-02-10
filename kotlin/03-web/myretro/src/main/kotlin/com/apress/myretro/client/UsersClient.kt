package com.apress.myretro.client

import com.apress.myretro.config.MyRetroProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.text.MessageFormat

@Component
class UsersClient(val USERS_URL: String = "/users",
                  val restTemplate: RestTemplate = RestTemplate(),
                  val myRetroProperties: MyRetroProperties? = null) {
    fun findUserByEmail(email: String): User? {
        val uri: String = MessageFormat.format(
            "{0}:{1}{2}/{3}",
            myRetroProperties!!.users!!.server,
            myRetroProperties!!.users!!.port.toString(),
            USERS_URL, email
        )
        return restTemplate.getForObject(uri, User::class.java)
    }
}

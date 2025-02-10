package com.apress.myretro

import com.apress.myretro.client.UsersClient
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.web.client.RestTemplate

// the @users:UsersApplication.kt must be running, and
// see @users:resources/application.properties
@SpringBootTest
class UsersClientTest {
    @Autowired
    lateinit var usersClient: UsersClient

    @Test
    fun findUserTest() {
        val user = usersClient.findUserByEmail("norma@email.com")
        Assertions.assertThat(user).isNotNull()
        Assertions.assertThat(user!!.name).isEqualTo("Norma")
        Assertions.assertThat(user.email).isEqualTo("norma@email.com")
    }
}

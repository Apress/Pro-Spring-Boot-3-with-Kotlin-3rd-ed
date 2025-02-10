package com.apress.users

import com.apress.users.service.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UsersSmokeTests {
    @Autowired
    private lateinit var userService: UserService

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assertions.assertThat(userService).isNotNull()
    }
}

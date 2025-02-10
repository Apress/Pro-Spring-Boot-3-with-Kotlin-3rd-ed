package com.apress.users

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UsersSmokeTests {
    @Autowired
    private lateinit var controller: UsersController

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        Assertions.assertThat(controller).isNotNull()
    }
}

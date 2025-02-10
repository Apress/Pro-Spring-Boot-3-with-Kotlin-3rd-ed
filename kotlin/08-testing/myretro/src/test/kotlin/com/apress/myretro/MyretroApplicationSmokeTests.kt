package com.apress.myretro

import com.apress.myretro.web.RetroBoardController
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MyretroApplicationSmokeTests {
    @Autowired
    private lateinit var controller: RetroBoardController

    @Test
    @Throws(Exception::class)
    fun contextLoads() {
        assertThat(controller).isNotNull()
    }
}

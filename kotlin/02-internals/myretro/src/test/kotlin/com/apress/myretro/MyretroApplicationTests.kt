package com.apress.myretro

import com.apress.myretro.service.RetroBoardService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
internal class MyretroApplicationTests {
    val uuid = UUID.fromString("66D4A370-C312-4426-9C39-B411D0E43DAB")

    @Autowired
    var service: RetroBoardService? = null

    @Test
    fun happyCardTest() {
        val happyCards = service!!.findAllHappyCardsFromRetroBoardId(uuid)
        Assertions.assertThat(happyCards).isNotEmpty()
    }
}

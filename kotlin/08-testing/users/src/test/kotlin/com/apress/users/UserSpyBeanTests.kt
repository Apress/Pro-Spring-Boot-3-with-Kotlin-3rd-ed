package com.apress.users

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("spyBean")
class UserSpyBeanTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @SpyBean
    private lateinit var userRepository: UserRepository

    @Test
    @Throws(Exception::class)
    fun testGetAllUsers() {
        val mockUsers: List<User> = listOf(
            UserBuilder.createUser()
                .withName("Ximena")
                .withEmail("ximena@email.com")
                .build(),
            UserBuilder.createUser()
                .withName("Norma")
                .withEmail("norma@email.com")
                .build()
        )
        Mockito.doReturn(mockUsers).`when`(userRepository)!!.findAll()
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ximena"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Norma"))
        Mockito.verify(userRepository).findAll()
    }
}

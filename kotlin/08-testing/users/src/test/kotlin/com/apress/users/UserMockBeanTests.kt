package com.apress.users

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mockBean")
class UserMockBeanTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    @Throws(Exception::class)
    fun saveUsers() {
        val user = UserBuilder.createUser()
            .withName("Dummy")
            .withEmail("dummy@email.com")
            .active()
            .withRoles(UserRole.USER)
            .withPassword("aw3s0m3R!")
            .build()
        Mockito.`when`<Any>(userRepository.save(ArgumentMatchers.any())).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
        {
                            "email": "dummy@email.com",
                            "name": "Dummy",
                            "password": "aw2s0meR!",
                            "gravatarUrl": "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                            "userRole": ["USER"],
                            "active": true
                        }
                    
                    """.trimIndent()
                )
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userRole").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.userRole[0]").value("USER"))
        Mockito.verify(userRepository, Mockito.times(1)).save(
            Mockito.any(
                User::class.java
            )
        )
    }
}

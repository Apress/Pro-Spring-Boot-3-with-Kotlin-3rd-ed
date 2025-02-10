package com.apress.users

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@WithMockUser(authorities = ["SCOPE_users.read"])
@AutoConfigureMockMvc
class UserMockMvcTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @Throws(Exception::class)
    fun createUserTests() {
        val location = mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
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
            .andExpect(MockMvcResultMatchers.header().exists("Location"))
            .andReturn().response.getHeader("Location")
        mockMvc.perform(MockMvcRequestBuilders.get(location!!))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
    }

    @Throws(Exception::class)
    @Test
    fun allUsersTests() {
            mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ximena"))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$..active").value<Iterable<Boolean>>(Matchers.hasItem(true))
                )
        }
}

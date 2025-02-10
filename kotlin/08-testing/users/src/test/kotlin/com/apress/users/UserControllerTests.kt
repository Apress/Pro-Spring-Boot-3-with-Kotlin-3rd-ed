package com.apress.users

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders as MMRB
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@WebMvcTest(controllers = [UsersController::class])
class UserControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userRepository: UserRepository

    @Throws(Exception::class)
    @Test
    fun allUsersTest() {
            Mockito.`when`(userRepository.findAll()).thenReturn(
                listOf(
                    UserBuilder.createUser()
                        .withName("Ximena")
                        .withEmail("ximena@email.com")
                        .active()
                        .withRoles(UserRole.USER, UserRole.ADMIN)
                        .withPassword("aw3s0m3R!")
                        .build(),
                    UserBuilder.createUser()
                        .withName("Norma")
                        .withEmail("norma@email.com")
                        .active()
                        .withRoles(UserRole.USER)
                        .withPassword("aw3s0m3R!")
                        .build()
                )
            )
            mockMvc.perform(MMRB.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(true))
        }

    @Test
    @Throws(Exception::class)
    fun newUserTest() {
        val user = UserBuilder.createUser()
            .withName("Dummy")
            .withEmail("dummy@email.com")
            .active()
            .withRoles(UserRole.USER, UserRole.ADMIN)
            .withPassword("aw3s0m3R!")
            .build()
        Mockito.`when`(userRepository.save<User>(user)).thenReturn(user)
        mockMvc.perform(
            MMRB.post("/users")
                .content(toJson(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"))
    }

    @Test
    @Throws(Exception::class)
    fun findUserByEmailTest() {
        val user = UserBuilder.createUser()
            .withName("Dummy")
            .withEmail("dummy@email.com")
            .active()
            .withRoles(UserRole.USER, UserRole.ADMIN)
            .withPassword("aw3s0m3R!")
            .build()
        Mockito.`when`<Optional<User>>(userRepository.findById(user.email!!)).thenReturn(
            Optional.of<User>(user)
        )
        mockMvc.perform(
            MMRB.get("/users/{email}", user.email)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"))
    }

    @Test
    @Throws(Exception::class)
    fun deleteUserByEmailTest() {
        val user = UserBuilder.createUser()
            .withEmail("dummy@email.com")
            .build()
        Mockito.doNothing().`when`<UserRepository?>(userRepository).deleteById(user.email!!)
        mockMvc.perform(MMRB.delete("/users/{email}", user.email))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    companion object {
        private fun toJson(obj: Any?): String = ObjectMapper().writeValueAsString(obj)
    }
}

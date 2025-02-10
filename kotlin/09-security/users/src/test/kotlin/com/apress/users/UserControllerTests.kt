package com.apress.users

import com.apress.users.config.UserConfig
import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.repository.UserRepository
import com.apress.users.security.UserSecurityConfig
import com.apress.users.web.UsersController
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@Import(UserSecurityConfig::class, UserConfig::class)
@WebMvcTest(controllers = [UsersController::class])
class UserControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @MockBean
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Throws(Exception::class)
    @Test
    @WithMockUser
    fun allUsersTest() {
            Mockito.`when`<Iterable<User>>(userRepository.findAll()).thenReturn(
                listOf(
                    User(
                        email="dummy1@email.com",
                        name="Dummy1",
                        gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                        password="aw2s0meR!",
                        userRole = mutableListOf(UserRole.USER),
                        active=true),
                    User(
                        email="dummy2@email.com",
                        name="Dummy2",
                        gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                        password="aw2s0meR!",
                        userRole = mutableListOf(UserRole.USER),
                        active=true)
                )
            )
            mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(true))
        }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun newUserTest() {
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        Mockito.`when`(userRepository.save(user)).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .content(toJson(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"))
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun findUserByEmailTest() {
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        Mockito.`when`(userRepository.findById(user.email!!)).thenReturn(Optional.of(user))
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/{email}", user.email)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"))
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun deleteUserByEmailTest() {
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        Mockito.doNothing().`when`(userRepository)!!.deleteById(user.email!!)
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{email}", user.email))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    companion object {
        private fun toJson(obj: Any): String = ObjectMapper().writeValueAsString(obj)
    }
}

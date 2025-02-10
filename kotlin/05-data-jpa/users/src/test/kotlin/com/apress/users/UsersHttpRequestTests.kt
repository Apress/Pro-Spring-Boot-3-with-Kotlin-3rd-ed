package com.apress.users

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersHttpRequestTests {
    @Value("\${local.server.port}")
    private val port = 0

    private val BASE_URL = "http://localhost:"
    private val USERS_PATH = "/users"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @Throws(Exception::class)
    fun indexPageShouldReturnHeaderOneContent() {
        Assertions.assertThat(
            restTemplate.getForObject(
                BASE_URL + port,
                String::class.java
            )
        ).contains("Simple Users Rest Application")
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        val response: Collection<User> =
            restTemplate.getForObject(BASE_URL + port + USERS_PATH, Collection::class.java) as Collection<User>
        Assertions.assertThat(response.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val user: User = User(
            email = "dummy@email.com",
            name = "Dummy",
            gravatarUrl = "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
            password = "aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active = true)
        val response = restTemplate.postForObject(BASE_URL + port + USERS_PATH, user, User::class.java)
        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response.email).isEqualTo(user.email)
        val users: Collection<User> =
            restTemplate.getForObject(BASE_URL + port + USERS_PATH, Collection::class.java) as Collection<User>
        Assertions.assertThat(users.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        restTemplate.delete("$BASE_URL$port$USERS_PATH/norma@email.com")
        val users: Collection<User> =
            restTemplate.getForObject(BASE_URL + port + USERS_PATH, Collection::class.java) as Collection<User>
        Assertions.assertThat(users.size).isLessThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        val user = restTemplate.getForObject("$BASE_URL$port$USERS_PATH/ximena@email.com", User::class.java)
        Assertions.assertThat(user).isNotNull()
        Assertions.assertThat(user.email).isEqualTo("ximena@email.com")
    }
}

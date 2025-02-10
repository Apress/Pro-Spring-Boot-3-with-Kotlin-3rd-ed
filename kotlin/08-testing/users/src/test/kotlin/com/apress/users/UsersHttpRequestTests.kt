package com.apress.users

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("integration")
@Testcontainers
class UsersHttpRequestTests {
    private val USERS_PATH = "/users"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @Throws(Exception::class)
    fun indexPageShouldReturnHeaderOneContent() {
        assertThat(
            restTemplate.getForObject("/", String::class.java)
        ).contains("Simple Users Rest Application")
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        val response: Collection<User> =
            restTemplate.getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(response.size).isEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val user = User(
            "dummy@email.com", "Dummy",
            "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
            "aw2s0me", mutableListOf(UserRole.USER), true
        )
        val response = restTemplate.postForObject(USERS_PATH, user, User::class.java)
        assertThat(response).isNotNull()
        assertThat(response.email).isEqualTo(user.email)
        val users: Collection<User> =
            restTemplate.getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(users.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        restTemplate.delete("$USERS_PATH/norma@email.com")
        val users: Collection<User> =
            restTemplate.getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(users.size).isLessThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        val user = restTemplate.getForObject("$USERS_PATH/ximena@email.com", User::class.java)
        assertThat(user).isNotNull()
        assertThat(user.email).isEqualTo("ximena@email.com")
    }

    companion object {
        @Container
        @ServiceConnection
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}

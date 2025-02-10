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
        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response).isNotEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val user: User = User(email = "dummy@email.com", name = "Dummy", password = "aw2s0m3R!")
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

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnBadUserResponseIfBadPasswd() {
        val user: User = User(email = "dummy@email.com", name = "Dummy", password = "aw2s0m")
        val response: Map<*, *> =
            restTemplate.postForObject(BASE_URL + port + USERS_PATH, user, Map::class.java)
        Assertions.assertThat<Any>(response).isNotNull()
        Assertions.assertThat(response["errors"]).isNotNull()
        val errors = response["errors"] as Map<*, *>?
        Assertions.assertThat(errors!!["password"]).isNotNull()
        Assertions.assertThat(errors["password"])
            .isEqualTo("Password must be at least 8 characters long and contain at least one number, one uppercase, one lowercase and one special character")
    }
}

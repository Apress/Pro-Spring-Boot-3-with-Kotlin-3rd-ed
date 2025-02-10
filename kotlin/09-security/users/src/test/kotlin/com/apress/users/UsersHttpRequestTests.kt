package com.apress.users

import com.apress.users.model.User
import com.apress.users.model.UserRole
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersHttpRequestTests {
    private val USERS_PATH = "/users"

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @Throws(Exception::class)
    fun indexPageShouldReturnHeaderOneContent() {
        val html = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!").getForObject("/", String::class.java)
        assertThat(html).contains("Simple Users Rest Application")
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        val response: Collection<User> = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!")
            .getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(response.size).isGreaterThan(1)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val user = User(
            "dummy@email.com", "Dummy",
            "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
            "SomeOtherAw2s0meR!", mutableListOf(UserRole.USER), true
        )
        val response = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!")
            .postForObject(USERS_PATH, user, User::class.java)
        assertThat(response).isNotNull()
        assertThat(response.email).isEqualTo(user.email)
        val users: Collection<User> = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!")
            .getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(users.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!").delete("$USERS_PATH/norma@email.com")
        val users: Collection<User> = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!")
            .getForObject(USERS_PATH, Collection::class.java) as Collection<User>
        assertThat(users.size).isLessThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        val user = restTemplate.withBasicAuth("manager@email.com", "aw2s0meR!").getForObject(
            "$USERS_PATH/ximena@email.com", User::class.java
        )
        assertThat(user).isNotNull()
        assertThat(user.email).isEqualTo("ximena@email.com")
    }
}

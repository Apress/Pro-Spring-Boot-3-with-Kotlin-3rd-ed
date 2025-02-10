package com.apress.users

import com.apress.users.model.User
import com.apress.users.model.UserRole
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Consumer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersHttpRequestTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Throws(Exception::class)
    fun indexPageShouldReturnHeaderOneContent() {
        webTestClient.get().uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String::class.java)
            .value { value: String? -> Assertions.assertThat(value).contains("Simple Users Rest Application") }
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        webTestClient.get().uri("/users")
            .exchange().expectStatus().isOk()
            .expectBody(Collection::class.java).value(Consumer { collection: Collection<*> ->
                Assertions.assertThat(collection.size).isGreaterThanOrEqualTo(3)
            })
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        webTestClient.post().uri("/users")
            .body(
                Mono.just(
                    User(
                        null,
                        "dummy@email.com",
                        "Dummy",
                        null,
                        "aw2s0me",
                        mutableListOf(UserRole.USER),
                        true
                    )
                ), User::class.java
            )
            .exchange().expectStatus().isOk()
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        webTestClient.delete().uri("/users/norma@email.com")
            .exchange().expectStatus().isNoContent()
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        webTestClient.get().uri("/users/ximena@email.com")
            .exchange().expectStatus().isOk()
            .expectBody(User::class.java).value { user: User ->
                Assertions.assertThat(user).isNotNull()
                Assertions.assertThat(user.email).isEqualTo("ximena@email.com")
            }
    }
}

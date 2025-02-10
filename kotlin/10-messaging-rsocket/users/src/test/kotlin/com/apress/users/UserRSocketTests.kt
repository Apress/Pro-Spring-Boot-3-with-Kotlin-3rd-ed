package com.apress.users

import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.repository.UserRepository
import io.rsocket.core.RSocketConnector
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.util.MimeTypeUtils
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import reactor.util.retry.Retry.RetrySignal
import java.util.*

@SpringBootTest
class UserRSocketTests {
    @Autowired
    private lateinit var rSocketRequester: RSocketRequester

    @Test
    fun testGetAllUsers() {
        // Send a request message
        val result = rSocketRequester
            .route("all-users")
            .retrieveFlux(User::class.java)

        // Verify that the response message contains the expected data
        StepVerifier
            .create(result)
            .consumeNextWith { (id): User ->
                Assertions.assertThat(
                    id
                ).isNotNull()
            }
            .thenCancel()
            .verify()
    }

    @TestConfiguration
    internal class ClientConfiguration {
        @Bean
        @Lazy
        fun rSocketRequester(rSocketStrategies: RSocketStrategies?): RSocketRequester {
            val builder = RSocketRequester.builder()
            return builder
                .rsocketStrategies { c: RSocketStrategies.Builder ->
                    c.encoder(Jackson2JsonEncoder())
                    c.decoder(Jackson2JsonDecoder())
                }
                .rsocketConnector { rSocketConnector: RSocketConnector ->
                    rSocketConnector.reconnect(
                        Retry.indefinitely().doAfterRetry { e: RetrySignal -> println(e.failure()) })
                }
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 9898)
        }

        @Bean
        fun init(userRepository: UserRepository): CommandLineRunner {
            return CommandLineRunner { _: Array<String> ->
                userRepository.save(
                    User(
                        null, "ximena@email.com", "Ximena", null, "aw2s0me", Arrays.asList(
                            UserRole.USER
                        ), true
                    )
                ).block()
                userRepository.save(
                    User(
                        null,
                        "norma@email.com",
                        "Norma",
                        null,
                        "aw2s0me",
                        Arrays.asList(UserRole.USER, UserRole.ADMIN),
                        false
                    )
                ).block()
                userRepository.save(
                    User(
                        null,
                        "dummy@email.com",
                        "Dummy",
                        null,
                        "aw2s0me",
                        Arrays.asList(UserRole.USER, UserRole.ADMIN),
                        false
                    )
                ).block()
            }
        }
    }
}

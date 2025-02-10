package com.apress.users

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsersHttpRequestTests {
    private var baseUrl: String? = null

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        baseUrl = "/users"
    }

    @Test
    @Throws(Exception::class)
    fun usersEndPointShouldReturnCollectionWithTwoUsers() {
        val response: ResponseEntity<CollectionModel<EntityModel<User>>> =
            restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<CollectionModel<EntityModel<User>>>() {})
        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body).isNotNull()
        Assertions.assertThat(response.headers.getContentType()).isEqualTo(MediaTypes.HAL_JSON)
        Assertions.assertThat(response.body!!.content.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointPostNewUserShouldReturnUser() {
        val createHeaders = HttpHeaders()
        createHeaders.contentType = MediaTypes.HAL_JSON
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
            password = "aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        val createRequest = HttpEntity(convertToJson(user), createHeaders)
        val response: ResponseEntity<EntityModel<User>> = restTemplate.exchange(
            baseUrl,
            HttpMethod.POST,
            createRequest,
            object : ParameterizedTypeReference<EntityModel<User>>() {})
        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        val userResponse = response.body!!
        Assertions.assertThat(userResponse).isNotNull()
        Assertions.assertThat(userResponse.content).isNotNull()
        Assertions.assertThat(userResponse.getLink("self")).isNotNull()
        Assertions.assertThat(userResponse.content!!.email).isEqualTo(user.email)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointDeleteUserShouldReturnVoid() {
        restTemplate.delete("$baseUrl/1")
        val response: ResponseEntity<CollectionModel<EntityModel<User>>> =
            restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<CollectionModel<EntityModel<User>>>() {})
        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body).isNotNull()
        Assertions.assertThat(response.headers.getContentType()).isEqualTo(MediaTypes.HAL_JSON)
        Assertions.assertThat(response.body!!.content.size).isGreaterThanOrEqualTo(1)
    }

    @Test
    @Throws(Exception::class)
    fun userEndPointFindUserShouldReturnUser() {
        val email = "ximena@email.com"
        val response: ResponseEntity<EntityModel<User>> = restTemplate.exchange(
            "$baseUrl/search/findByEmail?email={email}",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<EntityModel<User>>() {},
            email
        )
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val users = response.body!!
        Assertions.assertThat(users).isNotNull()
        Assertions.assertThat(users.content!!.email).isEqualTo(email)
    }

    @Throws(JsonProcessingException::class)
    private fun convertToJson(user: User): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(user)
    }
}

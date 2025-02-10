package com.apress.users

import com.apress.users.model.User
import com.apress.users.model.UserBuilder
import com.apress.users.model.UserGravatar.getGravatarUrlFromEmail
import com.apress.users.model.UserRole
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.io.IOException

@JsonTest
class UserJsonTests {
    @Autowired
    private lateinit var jacksonTester: JacksonTester<User>

    @Test
    @Throws(IOException::class)
    fun serializeUserJsonTest() {
        val user = UserBuilder.createUser(Validation.buildDefaultValidatorFactory().validator)
            .withEmail("dummy@email.com")
            .withPassword("aw2s0me")
            .withName("Dummy")
            .withRoles(UserRole.USER)
            .active().build()
        val json = jacksonTester.write(user)
        assertThat(json).extractingJsonPathValue("$.email").isEqualTo("dummy@email.com")
        assertThat(json).extractingJsonPathArrayValue<Any>("$.userRole").size().isEqualTo(1)
        assertThat(json).extractingJsonPathBooleanValue("$.active").isTrue()
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isNotNull()
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isEqualTo(
            getGravatarUrlFromEmail(
                user.email!!
            )
        )
    }

    @Test
    @Throws(IOException::class)
    fun serializeUserJsonFileTest() {
        val user = UserBuilder.createUser(Validation.buildDefaultValidatorFactory().validator)
            .withEmail("dummy@email.com")
            .withPassword("aw2s0me")
            .withName("Dummy")
            .withRoles(UserRole.USER)
            .active().build()
        println(user)
        val json = jacksonTester.write(user)
        assertThat(json).isEqualToJson("user.json")
    }

    @Test
    @Throws(Exception::class)
    fun deserializeUserJsonTest() {
        val userJson = """
                {
                  "email": "dummy@email.com",
                  "name": "Dummy",
                  "password": "aw2s0me",
                  "userRole": ["USER"],
                  "active": true
                }
                
                """.trimIndent()
        val user = jacksonTester.parseObject(userJson)
        assertThat(user.email).isEqualTo("dummy@email.com")
        assertThat(user.password).isEqualTo("aw2s0me")
        assertThat(user.active).isTrue()
    }

    /* Is necessary to modify the UserBuilder. */
    @Test
    fun userValidationTest(){
        assertThatExceptionOfType(
            ConstraintViolationException::class.java)
                .isThrownBy{ UserBuilder.createUser(Validation.buildDefaultValidatorFactory().validator)
                        .withEmail("dummy@email.com")
                        .withName("Dummy")
                        .withRoles(UserRole.USER)
                        .active().build()}

        // Junit 5
        val exception = assertThrows<ConstraintViolationException>(
            "Should throw exception"
        ) {
            UserBuilder.createUser(Validation.buildDefaultValidatorFactory().getValidator())
                    .withName("Dummy")
                    .withRoles(UserRole.USER)
                    .active().build()
        }

        val expectedMessage = "email: Email can not be empty"
        assertThat(exception.message).contains(expectedMessage)

    }
    /**/
}

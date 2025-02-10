package com.apress.users

import com.apress.users.model.User
import com.apress.users.model.UserGravatar.getGravatarUrlFromEmail
import com.apress.users.model.UserRole
import jakarta.validation.Validation
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.io.IOException

@JsonTest
class UserJsonTests {
    @Autowired
    private val jacksonTester: JacksonTester<User>? = null
    @Test
    @Throws(IOException::class)
    fun serializeUserJsonTest() {
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        val json = jacksonTester!!.write(user)
        assertThat(json).extractingJsonPathValue("$.email").isEqualTo("dummy@email.com")
        assertThat(json).extractingJsonPathArrayValue<Any>("$.userRole").size().isEqualTo(1)
        assertThat(json).extractingJsonPathBooleanValue("$.active").isTrue()
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isNotNull()
        assertThat(json).extractingJsonPathValue("$.gravatarUrl").isEqualTo(
            getGravatarUrlFromEmail(user.email!!)
        )
    }

    @Test
    @Throws(IOException::class)
    fun serializeUserJsonFileTest() {
        val user: User = User(
            email="dummy@email.com",
            name="Dummy",
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s0meR!",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        println(user)
        val json = jacksonTester!!.write(user)
        assertThat(json).isEqualToJson("user.json")
    }

    @Test
    @Throws(Exception::class)
    fun deserializeUserJsonTest() {
        val userJson = """
                {
                  "email": "dummy@email.com",
                  "name": "Dummy",
                  "password": "aw2s0meR!",
                  "userRole": ["USER"],
                  "gravatarUrl": "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                  "active": true
                }
                
                """.trimIndent()
        val user = jacksonTester!!.parseObject(userJson)
        assertThat(user.email).isEqualTo("dummy@email.com")
        assertThat(user.password).isEqualTo("aw2s0meR!")
        assertThat(user.active).isTrue()
    }

    private val validator = Validation.buildDefaultValidatorFactory().validator
    @Test
    fun userValidationTest() {
        val user1: User = User(
            gravatarUrl="https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            password="aw2s",
            userRole = mutableListOf(UserRole.USER),
            active=true)
        val constraintViolations = validator.validate(user1)
        assertThat(constraintViolations).isNotEmpty()
        assertThat(constraintViolations).hasSize(5)
    }
}

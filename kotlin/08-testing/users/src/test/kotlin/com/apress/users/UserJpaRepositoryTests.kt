package com.apress.users

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Import(UserConfiguration::class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserJpaRepositoryTests {
    @Autowired
    var userRepository: UserRepository? = null
    @Test
    fun findAllTest() {
        val expectedUsers = userRepository!!.findAll()
        assertThat(expectedUsers).isNotEmpty()
        assertThat(expectedUsers).isInstanceOf(Iterable::class.java)
        assertThat(expectedUsers).element(0).isInstanceOf(User::class.java)
        assertThat(expectedUsers).element(0).matches(User::active)
    }

    @Test
    fun saveTest() {
        val dummyUser = UserBuilder.createUser()
            .withName("Dummy")
            .withEmail("dummy@email.com")
            .active()
            .withRoles(UserRole.INFO)
            .withPassword("aw3s0m3R!")
            .build()
        val expectedUser = userRepository!!.save<User>(dummyUser)
        assertThat(expectedUser).isNotNull()
        assertThat(expectedUser).isInstanceOf(User::class.java)
        assertThat(expectedUser).hasNoNullFieldsOrProperties()
        assertThat(expectedUser.active).isTrue()
    }

    @Test
    fun findByIdTest() {
        val expectedUser = userRepository!!.findById("norma@email.com")
        assertThat(expectedUser).isNotNull()
        assertThat(expectedUser.get()).isInstanceOf(User::class.java)
        assertThat(expectedUser.get().active).isTrue()
        assertThat(expectedUser.get().name).isEqualTo("Norma")
    }

    @Test
    fun deleteByIdTest() {
        var expectedUser = userRepository!!.findById("ximena@email.com")
        assertThat(expectedUser).isNotNull()
        assertThat(expectedUser.get()).isInstanceOf(User::class.java)
        assertThat(expectedUser.get().active).isTrue()
        assertThat(expectedUser.get().name).isEqualTo("Ximena")
        userRepository!!.deleteById("ximena@email.com")
        expectedUser = userRepository!!.findById("ximena@email.com")
        assertThat(expectedUser).isNotNull()
        assertThat(expectedUser).isEmpty()
    }

    companion object {
        @Container
        @ServiceConnection
        var postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}

package com.apress.users

import com.apress.users.config.UserConfig
import com.apress.users.model.User
import com.apress.users.model.UserRole
import com.apress.users.repository.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import java.util.function.Predicate

@Import(UserConfig::class)
@DataJpaTest
class UserJpaRepositoryTests {
    @Autowired
    var userRepository: UserRepository? = null

    @Test
    fun findAllTest() {
        val expectedUsers = userRepository!!.findAll()
        assertThat(expectedUsers).isNotEmpty()
        assertThat(expectedUsers).isInstanceOf(Iterable::class.java)
        assertThat(expectedUsers).element(0).isInstanceOf(
            User::class.java
        )
        assertThat(expectedUsers).element(0).matches(User::active)
    }

    @Test
    fun saveTest() {
        val dummyUser = User(
            "dummy@email.com",
            "Dummy",
            "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
            "aw2s0meR!",
            mutableListOf(UserRole.USER),
            true
        )
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
}

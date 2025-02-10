package com.apress.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.Statement
import java.sql.Types

@Repository
class UserRepository : SimpleRepository<User, Int> {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun findById(id: Int): User? {
        val sql = "SELECT * FROM users WHERE id = ?"
        val params = arrayOf<Any>(id)
        val user = jdbcTemplate.queryForObject(sql, params, intArrayOf(Types.INTEGER), UserRowMapper())
        return user
    }

    override fun findAll(): Iterable<User> {
        val sql = "SELECT * FROM users"
        return jdbcTemplate.query(sql, UserRowMapper())
    }

    override fun save(user: User): User {
        val sql =
            "INSERT INTO users (name, email, password, gravatar_url,user_role,active) VALUES (?, ?, ?, ?, ?, ?)"
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection: Connection ->
            val array: Array<String> = user.userRole!!.map(UserRole::name).toTypedArray()
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).apply {
                setString(1, user.name)
                setString(2, user.email)
                setString(3, user.password)
                setString(4, user.gravatarUrl)
                setArray(5, connection.createArrayOf("varchar", array))
                setBoolean(6, user.active)
            }
        }, keyHolder)
        return user.withId(keyHolder.keys!!["id"] as Int)
    }

    override fun deleteById(id: Int) {
        val sql = "DELETE FROM users WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }
}

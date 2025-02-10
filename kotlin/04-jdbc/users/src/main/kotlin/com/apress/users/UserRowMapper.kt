package com.apress.users

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors

class UserRowMapper : RowMapper<User> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        val array = rs.getArray("user_role")
        val roles = (array.array as Array<Any>).map(Any::toString).
            map{UserRole.valueOf(it)}.toMutableList()
        return User(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            email = rs.getString("email"),
            password = rs.getString("password"),
            active = rs.getBoolean("active"),
            userRole = roles)
    }
}

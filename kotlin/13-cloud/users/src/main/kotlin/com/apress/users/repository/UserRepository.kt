package com.apress.users.repository

import com.apress.users.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>

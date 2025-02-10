package com.apress.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>

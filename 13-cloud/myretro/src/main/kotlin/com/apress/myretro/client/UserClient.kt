package com.apress.myretro.client

import com.apress.myretro.client.model.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "users-service")
interface UserClient {
    @get:GetMapping("/users")
    var allUsers: ResponseEntity<Iterable<User?>?>?

    @GetMapping("/users/{email}")
    fun getById(@PathVariable email: String?): ResponseEntity<User?>?
}

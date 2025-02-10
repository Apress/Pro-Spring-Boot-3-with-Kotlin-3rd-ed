package com.apress.myretro

import org.springframework.ai.chat.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleAiController @Autowired constructor(private val chatClient: ChatClient) {
    @GetMapping("/ai/simple")
    fun completion(
        @RequestParam(
            value = "message",
            defaultValue = "Tell me a joke"
        ) message: String?
    ): Map<String, String> {
        return mapOf("generation" to chatClient.call(message))
    }
}

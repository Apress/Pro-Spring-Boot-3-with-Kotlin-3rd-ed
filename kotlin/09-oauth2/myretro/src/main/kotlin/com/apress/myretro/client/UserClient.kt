package com.apress.myretro.client

import com.apress.myretro.config.MyRetroProperties
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

//@Component
class UserClient(webClientBuilder: WebClient.Builder, props: MyRetroProperties) {
    private val webClient: WebClient

    init {
        webClient = webClientBuilder
            .baseUrl(props.users!!.server!!)
            .defaultHeaders { headers: HttpHeaders ->
                headers.setBasicAuth(
                    props.users!!.username!!,
                    props.users!!.password!!
                )
            }
            .build()
    }

    fun getUserInfo(email: String): Mono<User> {
        return webClient.get()
            .uri("/users/{email}", email)
            .retrieve()
            .bodyToMono(User::class.java)
    }

    fun getUserGravatar(email: String?): Mono<String> {
        return webClient.get()
            .uri("/users/{email}/gravatar", email)
            .retrieve()
            .bodyToMono(String::class.java)
    }
}

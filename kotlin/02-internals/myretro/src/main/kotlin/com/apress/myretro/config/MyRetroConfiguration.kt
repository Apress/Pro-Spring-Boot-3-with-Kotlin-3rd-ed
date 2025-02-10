package com.apress.myretro.config

import com.apress.myretro.persistence.RetroBoardRepository
import com.apress.myretro.service.RetroBoardService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean


@EnableConfigurationProperties(MyRetroProperties::class)
@Configuration
class MyRetroConfiguration {
    @Bean
    fun retroBoardService(): RetroBoardService {
        return RetroBoardService(RetroBoardRepository())
    }
}
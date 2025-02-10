package com.apress.myretro.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.MappedInterceptor

@Configuration
class RetroBoardMetrics {
    @Bean
    fun observedAspect(registry: ObservationRegistry?): ObservedAspect {
        return ObservedAspect(registry!!)
    }

    @Bean
    fun retroBoardCounter(registry: MeterRegistry?): Counter {
        return Counter.builder("retro_boards").description("Number of Retro Boards").register(
            registry!!
        )
    }

    @Bean
    fun metricsInterceptor(registry: MeterRegistry): MappedInterceptor {
        return MappedInterceptor(arrayOf("/**"), RetroBoardMetricsInterceptor(registry))
    }
}

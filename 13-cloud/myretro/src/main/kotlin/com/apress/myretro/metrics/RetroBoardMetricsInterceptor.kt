package com.apress.myretro.metrics

import io.micrometer.core.instrument.MeterRegistry
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor

class RetroBoardMetricsInterceptor(val meterRegistry: MeterRegistry) : HandlerInterceptor {
    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val URI: String = request.getRequestURI()
        val METHOD: String = request.getMethod()
        if (!URI.contains("prometheus")) {
            LOG.info("URI: $URI METHOD: $METHOD")
            meterRegistry.counter("retro_board_api", "URI", URI, "METHOD", METHOD).increment()
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RetroBoardMetricsInterceptor::class.java)
    }
}

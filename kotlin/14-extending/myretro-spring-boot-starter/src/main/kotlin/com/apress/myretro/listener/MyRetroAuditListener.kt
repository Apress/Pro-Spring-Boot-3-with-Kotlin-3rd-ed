package com.apress.myretro.listener

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener

class MyRetroAuditListener : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        println("MyRetroListener: ApplicationReadyEvent received")
    }
}

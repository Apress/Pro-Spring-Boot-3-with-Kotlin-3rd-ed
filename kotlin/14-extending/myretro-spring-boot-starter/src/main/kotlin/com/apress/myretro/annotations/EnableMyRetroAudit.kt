package com.apress.myretro.annotations

import com.apress.myretro.configuration.MyRetroAuditConfiguration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(
    MyRetroAuditConfiguration::class
)
annotation class EnableMyRetroAudit(val storage: MyRetroAuditStorage = MyRetroAuditStorage.DATABASE)

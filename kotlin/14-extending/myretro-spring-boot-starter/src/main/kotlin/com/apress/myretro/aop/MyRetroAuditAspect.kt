package com.apress.myretro.aop

import com.apress.myretro.annotations.MyRetroAudit
import com.apress.myretro.annotations.MyRetroAuditIntercept
import com.apress.myretro.annotations.MyRetroAuditStorage
import com.apress.myretro.configuration.MyRetroAuditProperties
import com.apress.myretro.formats.MyRetroAuditFormatStrategy
import com.apress.myretro.formats.MyRetroAuditFormatStrategyFactory
import com.apress.myretro.model.MyRetroAuditEvent
import com.apress.myretro.model.MyRetroAuditEventRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Aspect
class MyRetroAuditAspect(
    private var eventRepository: MyRetroAuditEventRepository,
    private var properties: MyRetroAuditProperties,
    private var storage: MyRetroAuditStorage) {

    @Around("@annotation(audit)")
    @Throws(Throwable::class)
    fun auditAround(joinPoint: ProceedingJoinPoint, audit: MyRetroAudit): Any {
        var myRetroEvent = MyRetroAuditEvent().apply {
            method = joinPoint.signature.name
            args = if (audit.showArgs) joinPoint.args.contentToString() else null
            message = audit.message
            if (audit.intercept == MyRetroAuditIntercept.BEFORE) {
                interceptor = MyRetroAuditIntercept.BEFORE.name
            } else if (audit.intercept == MyRetroAuditIntercept.AROUND) {
                interceptor = MyRetroAuditIntercept.AROUND.name
            }
        }

        val result: Any = joinPoint.proceed(joinPoint.args)

        myRetroEvent.result = result.toString()
        if (audit.intercept == MyRetroAuditIntercept.AFTER) {
            myRetroEvent.interceptor = MyRetroAuditIntercept.AFTER.name
        }

        // Database, Console or File
        if (storage == MyRetroAuditStorage.DATABASE) {
            myRetroEvent = eventRepository.save<MyRetroAuditEvent>(myRetroEvent)
        }

        // Logger or Console
        val formattedEvent = formatEvent(audit, myRetroEvent)
        if (properties.useLogger) {
            LOG.info("{}{}", properties.prefix, formattedEvent)
        } else {
            println(properties.prefix + formattedEvent)
        }
        return result
    }

    private fun formatEvent(audit: MyRetroAudit, myRetroEvent: MyRetroAuditEvent): String {
        val strategy: MyRetroAuditFormatStrategy = MyRetroAuditFormatStrategyFactory.getStrategy(audit.format)
        return if (audit.prettyPrint) strategy.prettyFormat(myRetroEvent) else strategy.format(myRetroEvent)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(MyRetroAuditAspect::class.java)
    }
}

package com.apress.myretro.configuration

import com.apress.myretro.annotations.EnableMyRetroAudit
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class MyRetroAuditCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean =
        context.beanFactory!!.getBeansWithAnnotation(EnableMyRetroAudit::class.java).isNotEmpty()
}

package com.apress.myretro.annotations

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class EnableMyRetroAuditValueProvider : BeanFactoryPostProcessor {
    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanName = beanFactory.getBeanNamesForAnnotation(
             EnableMyRetroAudit::class.java
        ).firstOrNull()
        if (beanName != null) {
            storage = beanFactory.findAnnotationOnBean(beanName, EnableMyRetroAudit::class.java)!!.storage
        }
    }

    companion object {
        var storage = MyRetroAuditStorage.DATABASE
            private set
    }
}

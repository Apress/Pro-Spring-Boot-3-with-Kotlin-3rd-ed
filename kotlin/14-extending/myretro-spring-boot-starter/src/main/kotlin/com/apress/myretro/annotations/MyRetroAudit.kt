package com.apress.myretro.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CLASS
)
annotation class MyRetroAudit(
    val showArgs: Boolean = false,
    val format: MyRetroAuditOutputFormat = MyRetroAuditOutputFormat.TXT,
    val intercept: MyRetroAuditIntercept = MyRetroAuditIntercept.BEFORE,
    val message: String = "",
    val prettyPrint: Boolean = false
)

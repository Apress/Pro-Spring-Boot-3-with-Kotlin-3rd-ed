package com.apress.myretro.formats

import com.apress.myretro.model.MyRetroAuditEvent

interface MyRetroAuditFormatStrategy {
    fun format(event: MyRetroAuditEvent): String
    fun prettyFormat(event: MyRetroAuditEvent): String
}

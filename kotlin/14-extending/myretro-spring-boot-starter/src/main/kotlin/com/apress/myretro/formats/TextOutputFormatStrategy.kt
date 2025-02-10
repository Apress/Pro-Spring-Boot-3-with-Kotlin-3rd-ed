package com.apress.myretro.formats

import com.apress.myretro.model.MyRetroAuditEvent

class TextOutputFormatStrategy : MyRetroAuditFormatStrategy {
    override fun format(event: MyRetroAuditEvent): String =
        event.toString()

    override fun prettyFormat(event: MyRetroAuditEvent): String =
        """
            
            
            $event
            
            """.trimIndent()
}

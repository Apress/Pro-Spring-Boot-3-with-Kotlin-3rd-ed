package com.apress.myretro.formats

import com.apress.myretro.model.MyRetroAuditEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

class JsonOutputFormatStrategy : MyRetroAuditFormatStrategy {
    private val objectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
    }

    override fun format(event: MyRetroAuditEvent): String =
        objectMapper.writeValueAsString(event)

    override fun prettyFormat(event: MyRetroAuditEvent): String =
        "\n\n" + objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(event) + "\n"
}

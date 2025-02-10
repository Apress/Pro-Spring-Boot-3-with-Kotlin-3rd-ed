package com.apress.myretro.formats

import com.apress.myretro.annotations.MyRetroAuditOutputFormat

object MyRetroAuditFormatStrategyFactory {
    fun getStrategy(outputFormat: MyRetroAuditOutputFormat?): MyRetroAuditFormatStrategy =
        when (outputFormat) {
            MyRetroAuditOutputFormat.JSON -> JsonOutputFormatStrategy()
            MyRetroAuditOutputFormat.TXT -> TextOutputFormatStrategy()
            else -> TextOutputFormatStrategy()
        }
}

package com.apress.myretro.advice

import com.apress.myretro.exception.RetroBoardNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
@Aspect
class RetroBoardAdvice {
    @Around("execution(* com.apress.myretro.persistence.RetroBoardRepository.findById(java.util.UUID))")
    @Throws(
        Throwable::class
    )
    fun checkFindRetroBoard(proceedingJoinPoint: ProceedingJoinPoint): Any {
        LOG.info("[ADVICE] {}", proceedingJoinPoint.signature.name)
        try {
            return proceedingJoinPoint.proceed(
                arrayOf<Any>(
                    UUID.fromString(proceedingJoinPoint.args[0].toString())
                ))
        }catch (e:NullPointerException) {
            throw RetroBoardNotFoundException()
        }
    }

    companion object {
        val LOG = LoggerFactory.getLogger(RetroBoardAdvice::class.java)
    }
}

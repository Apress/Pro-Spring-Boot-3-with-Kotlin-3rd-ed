package com.apress.myretro.metrics

import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationHandler
import org.slf4j.LoggerFactory

// Uncomment this to see the effect of the @Observed annotation (Custom Observation))
//@Component
class RetroBoardObservationHandler : ObservationHandler<Observation.Context?> {
    override fun onStart(context: Observation.Context) {
        LOG.info("Observation started with context: {}", context)
    }

    override fun onStop(context: Observation.Context) {
        LOG.info("Observation stopped with context: {}", context)
    }

    override fun supportsContext(context: Observation.Context) = true

    companion object {
        private val LOG = LoggerFactory.getLogger(RetroBoardObservationHandler::class.java)
    }
}

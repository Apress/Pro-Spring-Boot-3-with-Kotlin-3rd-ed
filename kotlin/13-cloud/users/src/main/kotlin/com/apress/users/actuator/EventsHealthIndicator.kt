package com.apress.users.actuator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.actuate.health.Status
import org.springframework.stereotype.Component

@Component
class EventsHealthIndicator : HealthIndicator {
    @Autowired
    private lateinit var logEventEndpoint: LogEventEndpoint

    override fun health(): Health {
        return if (check()) Health.up().build() else  // Custom Status
            Health.status(Status("EVENTS-DOWN", "Events are turned off!")).build()

        // Status
        //return Health.status(Status.DOWN).build();
    }

    private fun check(): Boolean {
        return logEventEndpoint.isEnable
    }
}

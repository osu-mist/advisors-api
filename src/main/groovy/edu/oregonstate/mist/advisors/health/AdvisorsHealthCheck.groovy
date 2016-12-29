package edu.oregonstate.mist.advisors.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result

class AdvisorsHealthCheck extends HealthCheck {

    @Override
    protected Result check() {
        try {
            String status = "green"

            if (status != "red") {
                return Result.healthy()
            }
            Result.unhealthy("status: ${status}")
        } catch(Exception e) {
            Result.unhealthy(e.message)
        }
    }
}

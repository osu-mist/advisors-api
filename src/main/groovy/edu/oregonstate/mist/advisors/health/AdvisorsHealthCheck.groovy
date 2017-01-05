package edu.oregonstate.mist.advisors.health

import com.codahale.metrics.health.HealthCheck
import com.codahale.metrics.health.HealthCheck.Result
import edu.oregonstate.mist.advisors.db.AdvisorDAO

class AdvisorsHealthCheck extends HealthCheck {
    private AdvisorDAO advisorDAO

    AdvisorsHealthCheck(AdvisorDAO advisorDAO) {
        this.advisorDAO = advisorDAO
    }

    @Override
    protected Result check() {
        try {
            String status = advisorDAO.checkHealth()

            if (status != null) {
                return Result.healthy()
            }
            Result.unhealthy("status: ${status}")
        } catch(Exception e) {
            Result.unhealthy(e.message)
        }
    }
}

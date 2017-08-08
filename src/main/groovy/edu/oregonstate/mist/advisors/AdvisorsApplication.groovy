package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.advisors.AdvisorsConfiguration
import edu.oregonstate.mist.advisors.db.AdvisorDAO
import edu.oregonstate.mist.advisors.health.AdvisorsHealthCheck
import edu.oregonstate.mist.advisors.resources.AdvisorsResource
import io.dropwizard.jdbi.DBIFactory
import edu.oregonstate.mist.api.Application
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class AdvisorsApplication extends Application<AdvisorsConfiguration> {
    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(AdvisorsConfiguration configuration, Environment environment) {
        this.setup(configuration, environment)

        DBIFactory factory = new DBIFactory()
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(),"jdbi")
        AdvisorDAO advisorDAO = jdbi.onDemand(AdvisorDAO.class)

        AdvisorsHealthCheck healthCheck = new AdvisorsHealthCheck(advisorDAO)
        environment.healthChecks().register("advisorsHealthCheck", healthCheck)

        environment.jersey().register(new AdvisorsResource(advisorDAO))
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new AdvisorsApplication().run(arguments)
    }
}

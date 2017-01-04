package edu.oregonstate.mist.webapiskeleton

import edu.oregonstate.mist.advisors.AdvisorsConfiguration
import edu.oregonstate.mist.advisors.db.AdvisorDAO
import edu.oregonstate.mist.advisors.health.AdvisorsHealthCheck
import edu.oregonstate.mist.advisors.resources.AdvisorsResource
import edu.oregonstate.mist.api.BuildInfoManager
import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.InfoResource
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.BasicAuthenticator
import edu.oregonstate.mist.api.PrettyPrintResponseFilter
import edu.oregonstate.mist.api.jsonapi.GenericExceptionMapper
import edu.oregonstate.mist.api.jsonapi.NotFoundExceptionMapper
import io.dropwizard.Application
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.auth.basic.BasicCredentialAuthFilter
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class AdvisorsApplication extends Application<AdvisorsConfiguration> {
    /**
     * Initializes application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<AdvisorsConfiguration> bootstrap) {}

    /**
     * Registers lifecycle managers and Jersey exception mappers
     * and container response filters
     *
     * @param environment
     * @param buildInfoManager
     */
    protected void registerAppManagerLogic(Environment environment,
                                           BuildInfoManager buildInfoManager) {

        environment.lifecycle().manage(buildInfoManager)

        environment.jersey().register(new NotFoundExceptionMapper())
        environment.jersey().register(new GenericExceptionMapper())
        environment.jersey().register(new PrettyPrintResponseFilter())
    }

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(AdvisorsConfiguration configuration, Environment environment) {
        Resource.loadProperties()
        BuildInfoManager buildInfoManager = new BuildInfoManager()
        registerAppManagerLogic(environment, buildInfoManager)

        DBIFactory factory = new DBIFactory()
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(),"jdbi")
        AdvisorDAO advisorDAO = jdbi.onDemand(AdvisorDAO.class)

        AdvisorsHealthCheck healthCheck = new AdvisorsHealthCheck(advisorDAO)
        environment.healthChecks().register("advisorsHealthCheck", healthCheck)

        environment.jersey().register(new InfoResource(buildInfoManager.getInfo()))
        environment.jersey().register(new AdvisorsResource())
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<AuthenticatedUser>()
                .setAuthenticator(new BasicAuthenticator(configuration.getCredentialsList()))
                .setRealm('SkeletonApplication')
                .buildAuthFilter()
        ))
        environment.jersey().register(new AuthValueFactoryProvider.Binder
                <AuthenticatedUser>(AuthenticatedUser.class))
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

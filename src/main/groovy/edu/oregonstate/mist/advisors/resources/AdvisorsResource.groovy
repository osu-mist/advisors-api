package edu.oregonstate.mist.advisors.resources

import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.Resource
import io.dropwizard.auth.Auth

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path('/advisors/')
@Produces(MediaType.APPLICATION_JSON)
class AdvisorsResource extends Resource {

    @GET
    Response getAdvisors(@Auth AuthenticatedUser _, @QueryParam('osuid') String osuid,
                         @QueryParam('onid') String onid) {

        if (onid && osuid) {
            return badRequest("Please specify either the osuid or the onid, but not both.").build()
        }
        if (!onid && !osuid) {
            return badRequest("Please specify either the osuid or the onid.").build()
        }

        ok('hello world').build()
    }
}

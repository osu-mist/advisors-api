package edu.oregonstate.mist.advisors.resources

import edu.oregonstate.mist.advisors.db.AdvisorDAO
import edu.oregonstate.mist.api.AuthenticatedUser
import edu.oregonstate.mist.api.Resource
import edu.oregonstate.mist.api.jsonapi.ResourceObject
import edu.oregonstate.mist.api.jsonapi.ResultObject
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
    private AdvisorDAO advisorDAO

    AdvisorsResource(AdvisorDAO advisorDAO) {
        this.advisorDAO = advisorDAO
    }

    @GET
    Response getAdvisors(@Auth AuthenticatedUser _, @QueryParam('osuid') String osuid,
                         @QueryParam('onid') String onid) {

        if (onid && osuid) {
            return badRequest("Please specify either the osuid or the onid, but not both.").build()
        }
        if (!onid && !osuid) {
            return badRequest("Please specify either the osuid or the onid.").build()
        }

        def id = onid ?: osuid
        def attributes = advisorDAO.getAdvisors(id)
        def resultObject = new ResultObject()
        attributes.each {
            resultObject.data += new ResourceObject(
                    id:         1, //@todo: what do we use for an id?
                    type:       'advisor',
                    attributes: it
            )
        }

        //@todo: validation
        ok(resultObject).build()
    }
}

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
    /**
     * Returns the primary advisor for a given student id
     *
     * @param String id         Either an onid username or osu id.
     */
    Response getPrimaryAdvisor(@Auth AuthenticatedUser _, @QueryParam('id') String id) {
        if (!id) {
            return badRequest("Please specify either the osuid or the onid.").build()
        }

        def attributes = advisorDAO.getAdvisors(id)
        def resultObject = new ResultObject()
        attributes.each {
            resultObject.data += new ResourceObject(
                    id:         it.hashCode(),
                    type:       'advisor',
                    attributes: it
            )
        }

        //@todo: validation
        ok(resultObject).build()
    }
}

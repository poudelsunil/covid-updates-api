package global.sunil.covidupdates.rest;

import global.sunil.covidupdates.utils.RestResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sunil on 2021-05-25 - १४:०६
 */
@RequestScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class CovidUpdateResource {

    @GET
    @Path("ping")
    public Response ping(){
        return RestResponse.ok();
    }

}

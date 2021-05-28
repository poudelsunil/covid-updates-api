package global.sunil.covidupdates.rest;

import global.sunil.covidupdates.lib.utils.RestResponse;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;
import global.sunil.covidupdates.rest.adaptors.SendEmailRequestAdaptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sunil on 2021-05-25 - १४:०६
 */
@RequestScoped
@Path("/ping")
@Named
public class PingResource {

    @GET
    public Response ping() {
        return RestResponse.ok();
    }
}

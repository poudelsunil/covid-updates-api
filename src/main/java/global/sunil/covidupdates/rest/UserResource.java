package global.sunil.covidupdates.rest;

import global.sunil.covidupdates.lib.utils.RestResponse;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;
import global.sunil.covidupdates.repositories.dtos.UserEmail;
import global.sunil.covidupdates.repositories.dtos.UserInfo;
import global.sunil.covidupdates.rest.adaptors.SendEmailRequestAdaptor;
import global.sunil.covidupdates.rest.adaptors.AddUserRequestAdaptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.JsonObject;

/**
 * @author Sunil on 2021-05-25 - १४:०६
 */
@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class UserResource {

    UserRepository userRepository;
    SendEmailRequestAdaptor sendEmailRequestAdaptor;
    AddUserRequestAdaptor addUserRequestAdaptor;

    @Inject
    UserResource(UserRepository userRepository ,
                 SendEmailRequestAdaptor sendEmailRequestAdaptor,
                 AddUserRequestAdaptor addUserRequestAdaptor) {
        this.userRepository = userRepository;
        this.sendEmailRequestAdaptor = sendEmailRequestAdaptor;
        this.addUserRequestAdaptor = addUserRequestAdaptor;
    }

    @POST
    public Response addUser(JsonObject jsonObject) {
        UserEmail userEmail = this.addUserRequestAdaptor.toServiceObject(jsonObject);
        return RestResponse.ok(userRepository.addUser(userEmail));
    }

    @GET
    public Response getUsers() {

        return RestResponse.ok(userRepository.getUsers());
    }

    @POST
    @Path("email")
    public Response email(JsonObject jsonObject) {

        SendEmailRequest request = this.sendEmailRequestAdaptor.toServiceObject(jsonObject);
        return RestResponse.ok(userRepository.sendEmails(request));
    }
}

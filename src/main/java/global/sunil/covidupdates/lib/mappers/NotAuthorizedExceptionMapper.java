package global.sunil.covidupdates.lib.mappers;


import global.sunil.covidupdates.utils.LoggerUtils;
import global.sunil.covidupdates.utils.RestResponse;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  private Logger logger = LoggerUtils.getLogger(NotAuthorizedExceptionMapper.class);

  @Override
  public Response toResponse(NotAuthorizedException exception) {
    Response response =
        Response.status(Response.Status.UNAUTHORIZED)
            .entity(
                RestResponse.error(
                        String.valueOf(Response.Status.UNAUTHORIZED.getStatusCode()),
                        Response.Status.UNAUTHORIZED.getReasonPhrase())
                    .toJson())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }
}

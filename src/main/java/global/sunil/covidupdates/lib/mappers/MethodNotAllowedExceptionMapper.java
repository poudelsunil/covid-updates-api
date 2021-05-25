package global.sunil.covidupdates.lib.mappers;

import global.sunil.covidupdates.utils.LoggerUtils;
import global.sunil.covidupdates.utils.RestResponse;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class MethodNotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

  private Logger logger = LoggerUtils.getLogger(MethodNotAllowedExceptionMapper.class);

  @Override
  public Response toResponse(NotAllowedException exception) {
    logger.log(Level.INFO, "METHOD NOT ALLOWED EXCEPTION :::{0}", exception.getMessage());

    Response response =
        Response.status(Response.Status.METHOD_NOT_ALLOWED)
            .entity(
                RestResponse.error(
                        String.valueOf(Response.Status.METHOD_NOT_ALLOWED.getStatusCode()),
                        "Method not allowed.")
                    .toJson())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }
}

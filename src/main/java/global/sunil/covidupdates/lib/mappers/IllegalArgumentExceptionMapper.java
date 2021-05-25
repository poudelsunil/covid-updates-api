package global.sunil.covidupdates.lib.mappers;

import global.sunil.covidupdates.utils.LoggerUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

  private Logger logger = LoggerUtils.getLogger(IllegalArgumentExceptionMapper.class);

  @Override
  public Response toResponse(IllegalArgumentException exception) {
    Response response =
        Response.status(Response.Status.BAD_REQUEST)
            .entity(toError(exception))
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    logger.log(Level.SEVERE, "EXCEPTION RESPONSE :: ", exception.getMessage());
    return response;
  }

  private JsonObject toError(IllegalArgumentException ex) {
    return Json.createObjectBuilder()
        .add("code", Response.Status.BAD_REQUEST.getStatusCode())
        .add("message", ex.getMessage())
        .build();
  }
}

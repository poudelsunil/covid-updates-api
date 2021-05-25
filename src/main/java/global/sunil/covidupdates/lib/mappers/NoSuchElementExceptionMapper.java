package global.sunil.covidupdates.lib.mappers;


import global.sunil.covidupdates.utils.LoggerUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {

  private Logger logger = LoggerUtils.getLogger(NoSuchElementExceptionMapper.class);

  @Override
  public Response toResponse(NoSuchElementException exception) {
    Response response =
        Response.status(Response.Status.BAD_REQUEST)
            .entity(toError(exception))
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }

  private JsonObject toError(NoSuchElementException ex) {
    return Json.createObjectBuilder()
        .add("code", Response.Status.BAD_REQUEST.getStatusCode())
        .add("message", "JSON PARSER ERROR")
        .build();
  }
}

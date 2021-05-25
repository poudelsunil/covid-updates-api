package global.sunil.covidupdates.lib.mappers;





import global.sunil.covidupdates.utils.LoggerUtils;
import global.sunil.covidupdates.utils.RestResponse;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class InternalServerExceptionMapper
    implements ExceptionMapper<InternalServerErrorException> {

  private Logger logger = LoggerUtils.getLogger(InternalServerExceptionMapper.class);

  @Override
  public Response toResponse(InternalServerErrorException exception) {
    logger.log(Level.INFO, "INTERNAL SERVER ERROR EXCEPTION :::{0}", exception.getMessage());
    Response response =
        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(
                RestResponse.error(
                        String.valueOf(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()),
                        "Internal Server Error.")
                    .toJson())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }
}

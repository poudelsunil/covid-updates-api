package global.sunil.covidupdates.lib.mappers;

import global.sunil.covidupdates.utils.LoggerUtils;
import global.sunil.covidupdates.utils.RestResponse;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class UnSupportedMediaTypeExceptionMapper implements ExceptionMapper<NotSupportedException> {

  private Logger logger = LoggerUtils.getLogger(UnSupportedMediaTypeExceptionMapper.class);

  @Override
  public Response toResponse(NotSupportedException exception) {
    logger.log(Level.INFO, "UNSUPPORTED MEDIA TYPE EXCEPTION :::{0}", exception.getMessage());
    Response response =
        Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
            .entity(
                RestResponse.error(
                        String.valueOf(Response.Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode()),
                        "UnSupported Media Type")
                    .toJson())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }
}

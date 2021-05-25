package global.sunil.covidupdates.lib.filters;

import global.sunil.covidupdates.utils.LoggerUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Provider
public class CorsFilter implements ContainerResponseFilter {

  private Logger logger = LoggerUtils.getLogger(CorsFilter.class);

  @Override
  public void filter(
      ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
      responseContext.getHeaders().add(
          "Access-Control-Allow-Origin", "*");
//      responseContext.getHeaders().add(
//          "Access-Control-Allow-Credentials", "true");
      responseContext.getHeaders().add(
          "Access-Control-Allow-Headers",
          getAllowedHeaders(requestContext));
      responseContext.getHeaders().add(
          "Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");



    requestContext
        .getHeaders()
        .add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
    requestContext.getHeaders().add(HttpHeaders.EXPIRES, "0");
    requestContext.getHeaders().add("Pragma", "no-cache");
    }


  protected String getAllowedHeaders(ContainerRequestContext responseContext) {
    List<String> headers = null;
    if (responseContext.getHeaders() != null) {
      headers = responseContext.getHeaders().get("Access-Control-Allow-Headers");
    }
    if (headers == null) {
      headers = new ArrayList<>();
    }
    headers.add("origin");
    headers.add("accept");
    headers.add("content-type");
    headers.add("X-Requested-With");
    headers.add("X-XSRF-TOKEN");
    headers.add("Pragma");
    headers.add("User-Agent");
    headers.add("Lang");
    return headers.stream().collect(Collectors.joining(","));
  }
}

package global.sunil.covidupdates.lib.restclient;

import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.lib.utils.Jsons;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class HttpClient {

    private final Logger logger = Logger.getLogger(HttpClient.class.getName());

    private List<String> getHopHeaders() {
        List<String> headers = new ArrayList<>();
        headers.add("Connection");
        headers.add("Proxy-Connection");
        headers.add("Keep-Alive");
        headers.add("Proxy-Authenticate");
        headers.add("Proxy-Authorization");
        headers.add("Te");
        headers.add("Trailer");
        headers.add("Transfer-Encoding");
        headers.add("Upgrade");
        headers.add("Host");
        headers.add("content-length");
        return headers;
    }

    private boolean isHopHeader(String header) {
        return this.getHopHeaders().stream().filter(s -> s.equalsIgnoreCase(header)).count() > 0;
    }

    protected void addRequestHeaders(Map<String, String> headers, HttpRequest.Builder builder) {
        headers.forEach(
                (s, strings) -> {
                    if (!this.isHopHeader(s)) builder.header(s, String.join(",", strings));
                });
    }

    private <T> Optional<HttpClientResponse> send(
            String method, String url, Map<String, String> headers, String body, Duration duration) {
        try {
            var client = java.net.http.HttpClient.newBuilder().sslContext(SSLSupport.getSslContext()).build();
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
            if (!HelperUtils.isBlankOrNull(body))
                bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
            builder.method(method, bodyPublisher);
            this.addRequestHeaders(headers, builder);
            HttpRequest httpClientRequest = builder.uri(new URI(url)).timeout(duration).build();
            HttpResponse<String> serviceResponse =
                    client.send(
                            httpClientRequest, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));
            logger.info("Got service response with status code ::: " + serviceResponse.statusCode());

            return Optional.of(new HttpClientResponse(serviceResponse.statusCode(), Jsons.fromJsonToObj(serviceResponse.body(), Object.class)));

        } catch (URISyntaxException | InterruptedException | IOException e) {
            logger.severe(" ERROR ::" + e.getMessage());
            return Optional.of(new HttpClientResponse(-1, e.getMessage()));
        }
    }


    public Optional<HttpClientResponse> get(HttpClientRequest request) {
        return this.send("GET", request.getUrl(), request.getHeaders(), null, request.getDuration());
    }

    public Optional<HttpClientResponse> post(HttpClientRequest request) {
        return this.send(
                "POST", request.getUrl(), request.getHeaders(), request.getBody(), request.getDuration());
    }
}
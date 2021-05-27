package global.sunil.covidupdates.lib.restclient;

import global.sunil.covidupdates.lib.exceptions.ExceptionType;
import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.lib.utils.Jsons;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Sunil on 2021-05-25 - १८:५३
 */
public class HttpClientUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    public static Map<String, String> prepareHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        return headers;
    }

    public static <T>  HttpClientRequest prepareHttpClientRequest(String url, Map headers, String body) {
        return new HttpClientRequest.Builder<T>()
                .with(
                        $ -> {
                            $.url = url;
                            $.headers = headers;
                            $.body = body;
                        })
                .build();
    }

    public static <T> HttpClientRequest prepareHttpClientRequest(String serviceUrl, Object request) {
        try {
            if (request == null) {
                return prepareHttpClientRequest(
                        serviceUrl, prepareHeaderMap(), null);
            } else {
                return prepareHttpClientRequest(
                        serviceUrl, prepareHeaderMap(), Jsons.toJsonObj(request));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw getCouldNotPrepareRequestForModuleCallException(e.getMessage());
        }
    }

    private static AppException getCouldNotPrepareRequestForModuleCallException(String message) {
        return prepareAppException(
                "ERROR_COVID_API_SERVICE",
                "Could not prepare request for error: " + message);
    }

    private static AppException prepareAppException(String code, String message) {
        LOGGER.info("Error code : " + code + " message : " + message);
        return new AppException(
                new ExceptionType() {
                    @Override
                    public String getCode() {
                        return code;
                    }

                    @Override
                    public String getDescription() {
                        return message;
                    }
                });
    }

    public static void throwIfExceptionOnResponse(HttpClientResponse response) {
        if (200 != response.getCode()) {

            LOGGER.info(
                    "Error response from Covid 19 service : " + Jsons.toJsonObj(response));

            throw prepareAppException(
                    response.getCode()+"", "[ Covid19 service ] " + Jsons.toJsonObj(response));
        }
    }

    public static Optional<HttpClientResponse> getHttpClientResponse(HttpClientRequest httpClientRequest) {

        try {
            return new HttpClient().post(httpClientRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw getCouldNotGetResponseFromModuleCallException(e.getMessage());
        }
    }

    public static Optional<HttpClientResponse> getHttpClientResponseForGet(
            HttpClientRequest httpClientRequest) {

        try {
            return new HttpClient().get(httpClientRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw getCouldNotGetResponseFromModuleCallException(e.getMessage());
        }
    }

    private static AppException getCouldNotGetResponseFromModuleCallException(String message) {
        return prepareAppException(
                "ERROR_COVID_API_SERVICE",
                "Could not get response from Covid 19 service module, error: " + message);
    }
}

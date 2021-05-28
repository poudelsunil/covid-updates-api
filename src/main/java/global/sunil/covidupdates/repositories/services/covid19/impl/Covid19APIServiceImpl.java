package global.sunil.covidupdates.repositories.services.covid19.impl;

import global.sunil.covidupdates.lib.restclient.HttpClient;
import global.sunil.covidupdates.lib.restclient.HttpClientRequest;
import global.sunil.covidupdates.lib.restclient.HttpClientResponse;
import global.sunil.covidupdates.lib.restclient.HttpClientUtils;
import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.lib.utils.Jsons;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.services.covid19.Covid19APIService;
import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryInfo;
import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryWiseCovidInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Sunil on 2021-05-25 - १७:४१
 */
@Named
@Dependent
public class Covid19APIServiceImpl implements Covid19APIService {

    private final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    @Inject
    @ConfigProperty(name = "COVID19_SERVICE_URL", defaultValue = "")
    private String covid19ServiceUrl;

    @Override
    public List<ServiceCountryInfo> getCountries() {


        String serviceUri = covid19ServiceUrl + "/countries";
        HttpClientRequest httpClientRequest =
                HttpClientUtils.prepareHttpClientRequest(serviceUri, null);

        LOGGER.info("URL:: " + serviceUri);
        Optional<HttpClientResponse> httpClientResponse = HttpClientUtils.getHttpClientResponseForGet(httpClientRequest);
        if (!httpClientResponse.isPresent()) {
            LOGGER.info("COULD NOT GET SERVICE RESPONSE");
            return Collections.emptyList();
        }

        HttpClientUtils.throwIfExceptionOnResponse(httpClientResponse.get());

        if (httpClientResponse.get().getCode() != 200) {
            try {
                String errorMessage = Jsons.fromJsonToObj(Jsons.toJsonObj(httpClientResponse.get().getData()), ErrorResponse.class).getMessage();
                if (!HelperUtils.isBlankOrNull(errorMessage)) {
                    ExceptionManager.throwCountriesNotFoundExceptionWithCustomMessage(errorMessage);
                }
            } catch (Exception e) {
            }

            ExceptionManager.throwException(ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRIES);
        }


        return Jsons.fromJsonToList(
                Jsons.toJsonObj(httpClientResponse.get().getData()), ServiceCountryInfo[].class);

    }

    @Override
    public List<ServiceCountryWiseCovidInfo> getCountryWiseCovidInfo(String countryCode) {

        String serviceUri = covid19ServiceUrl + "/dayone/country/" + countryCode;
        HttpClientRequest httpClientRequest = HttpClientUtils.prepareHttpClientRequest(serviceUri, null);

        LOGGER.info("URL:: " + serviceUri);
        Optional<HttpClientResponse> httpClientResponse = HttpClientUtils.getHttpClientResponseForGet(httpClientRequest);
        if (!httpClientResponse.isPresent()) {
            LOGGER.info("COULD NOT GET SERVICE RESPONSE");
            return Collections.emptyList();
        }

        HttpClientUtils.throwIfExceptionOnResponse(httpClientResponse.get());

        if (httpClientResponse.get().getCode() != 200) {
            try {
                String errorMessage = Jsons.fromJsonToObj(Jsons.toJsonObj(httpClientResponse.get().getData()), ErrorResponse.class).getMessage();
                if (!HelperUtils.isBlankOrNull(errorMessage)) {
                    ExceptionManager.throwCountryWiseCovidInfoNotFoundExceptionWithCustomMessage(errorMessage);
                }
            } catch (Exception e) {
            }
            ExceptionManager.throwException(ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRY_WISE_COVID_INFO);
        }

        return Jsons.fromJsonToList(
                Jsons.toJsonObj(httpClientResponse.get().getData()), ServiceCountryWiseCovidInfo[].class);
    }
}

class ErrorResponse {
    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

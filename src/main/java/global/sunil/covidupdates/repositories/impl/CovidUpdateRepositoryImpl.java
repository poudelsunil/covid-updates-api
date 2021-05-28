package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.repositories.CovidUpdatesRepository;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.dtos.CountryInfo;
import global.sunil.covidupdates.repositories.dtos.CountryWiseCovidInfo;
import global.sunil.covidupdates.repositories.dtos.GetCountryWiseCovidInfoRequest;
import global.sunil.covidupdates.repositories.services.covid19.Covid19APIService;
import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryInfo;
import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryWiseCovidInfo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sunil on 2021-05-25 - १५:४१
 */
@Named
public class CovidUpdateRepositoryImpl implements CovidUpdatesRepository {

    Covid19APIService covid19APIService;

    @Inject
    CovidUpdateRepositoryImpl(Covid19APIService covid19APIService) {
        this.covid19APIService = covid19APIService;
    }

    @Override
    public List<CountryInfo> getCountries() {

        List<ServiceCountryInfo> serviceCountries = covid19APIService.getCountries();

        return this.convertServiceCountriesToCountries(serviceCountries);
    }

    private List<CountryInfo> convertServiceCountriesToCountries(List<ServiceCountryInfo> serviceCountries) {
        return serviceCountries.stream()
                .map(serviceCountryInfo
                        -> this.convertServiceCountryInfoToCountryInfo(serviceCountryInfo))
                .collect(Collectors.toList());
    }

    private CountryInfo convertServiceCountryInfoToCountryInfo(ServiceCountryInfo serviceCountryInfo) {
        return new CountryInfo(serviceCountryInfo.getCountry(), serviceCountryInfo.getISO2());
    }


    @Override
    public List<CountryWiseCovidInfo> getCountryWiseCovidInfo(GetCountryWiseCovidInfoRequest request) {

        this.validateCountryWiseCovidRequest(request);
        List<ServiceCountryWiseCovidInfo> countryWiseCovidInfo = covid19APIService.getCountryWiseCovidInfo(request.getIso2());
        return prepareCountryWiseCovidInfo(countryWiseCovidInfo);
    }

    void validateCountryWiseCovidRequest(GetCountryWiseCovidInfoRequest request) {
        if (request == null || HelperUtils.isBlankOrNull(request.getIso2())) {
            ExceptionManager.throwException(ExceptionManager.CovidUpdatesError.COUNTRY_ISO2_IS_MISSING);
        }

        if (request.getIso2() != null && request.getIso2().length() != 2) {
            ExceptionManager.throwException(ExceptionManager.CovidUpdatesError.COUNTRY_ISO2_IS_INVALID);
        }
    }

    List<CountryWiseCovidInfo> prepareCountryWiseCovidInfo(List<ServiceCountryWiseCovidInfo> covidInfoFromService) {

        List<CountryWiseCovidInfo> countryWiseCovidInfoWithoutRates = covidInfoFromService.stream()
                .map(s -> new CountryWiseCovidInfo(
                        s.getCountry(),
                        s.getConfirmed(),
                        s.getDeaths(),
                        s.getRecovered(),
                        s.getActive(),
                        s.getDate()
                ))
                .collect(Collectors.toList());

        return prepareCountryWiseCovidInfoWithAdditionalStats(countryWiseCovidInfoWithoutRates);
    }

    List<CountryWiseCovidInfo> prepareCountryWiseCovidInfoWithAdditionalStats(List<CountryWiseCovidInfo> countryWiseCovidInfoWithoutRates) {

        List<CountryWiseCovidInfo> covidInfoWithAdditionStats = new ArrayList<>();

        int previousConfirmed = 0;
        for (int i = 0; i < countryWiseCovidInfoWithoutRates.size(); i++) {

            CountryWiseCovidInfo covidInfo = countryWiseCovidInfoWithoutRates.get(i);
            double confirmedIncrementRate = calculateIncrementRate(previousConfirmed, covidInfo.getConfirmed());
            previousConfirmed = covidInfo.getConfirmed();
            covidInfo.setConfirmedIncrementRate(confirmedIncrementRate);
            covidInfo.setDeathsRate(calculatePercentage(covidInfo.getConfirmed(), covidInfo.getDeaths()));
            covidInfo.setRecoveredRate(calculatePercentage(covidInfo.getConfirmed(), covidInfo.getRecovered()));
            covidInfo.setActiveRate(calculatePercentage(covidInfo.getConfirmed(), covidInfo.getActive()));

            covidInfoWithAdditionStats.add(covidInfo);
        }

        return covidInfoWithAdditionStats;
    }

    double calculatePercentage(int total, int number) {
        return HelperUtils.formatNumber(( (double)number / (double) total) * 100);
    }

    double calculateIncrementRate(int previousCount, int newCount) {
        int total = (previousCount == 0) ? newCount : previousCount;

        double rate = (((double)newCount - (double)previousCount) / (double)total) * 100;

        return HelperUtils.formatNumber(rate);
    }

}

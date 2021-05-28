package global.sunil.covidupdates.repositories.services.covid19;


import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryInfo;
import global.sunil.covidupdates.repositories.services.covid19.domains.ServiceCountryWiseCovidInfo;

import java.util.List;

/**
 * @author Sunil on 2021-05-25 - १६:४६
 */
public interface Covid19APIService {
    List<ServiceCountryInfo> getCountries();
    List<ServiceCountryWiseCovidInfo> getCountryWiseCovidInfo(String countryCode);
}

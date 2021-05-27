package global.sunil.covidupdates.repositories;

import global.sunil.covidupdates.repositories.dtos.CountryInfo;
import global.sunil.covidupdates.repositories.dtos.CountryWiseCovidInfo;
import global.sunil.covidupdates.repositories.dtos.GetCountryWiseCovidInfoRequest;

import java.util.List;

/**
 * @author Sunil on 2021-05-25 - १५:२२
 */
public interface CovidUpdatesRepository {
    List<CountryInfo> getCountries();
    List<CountryWiseCovidInfo> getCountryWiseCovidInfo(GetCountryWiseCovidInfoRequest getCountryWiseCovidInfoRequest);
}
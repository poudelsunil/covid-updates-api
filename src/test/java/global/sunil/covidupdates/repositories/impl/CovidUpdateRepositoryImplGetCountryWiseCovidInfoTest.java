package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.repositories.CovidUpdatesRepository;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.dtos.CountryInfo;
import global.sunil.covidupdates.repositories.dtos.CountryWiseCovidInfo;
import global.sunil.covidupdates.repositories.dtos.GetCountryWiseCovidInfoRequest;
import global.sunil.covidupdates.repositories.services.Covid19APIService;
import global.sunil.covidupdates.repositories.services.domains.ServiceCountryInfo;
import global.sunil.covidupdates.repositories.services.domains.ServiceCountryWiseCovidInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sunil on 2021-05-27 - १६:११
 */
class CovidUpdateRepositoryImplGetCountryWiseCovidInfoTest {

    CovidUpdatesRepository covidUpdatesRepository;
    @BeforeEach
    void setup(){
        Covid19APIService covid19APIService = Mockito.mock(Covid19APIService.class);
        Mockito.when(covid19APIService.getCountryWiseCovidInfo("NP"))
                .thenReturn(Arrays.asList(
                        new ServiceCountryWiseCovidInfo("id", "Nepal", "NP", 10, 0, 8, 2, "2020-01-25T00:00:00Z"),
                        new ServiceCountryWiseCovidInfo("id", "Nepal", "NP", 12, 0, 9, 3, "2020-01-26T00:00:00Z")
                ));

        Mockito.when(covid19APIService.getCountryWiseCovidInfo("XX")).thenThrow(
                new AppException(ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRY_WISE_COVID_INFO));

        covidUpdatesRepository = new CovidUpdateRepositoryImpl(covid19APIService);

    }



    @Test
    @DisplayName("Should throw country iso2 is missing exception")
    void shouldThrowCountryIso2IsMissing() {

        AppException exception =
                assertThrows(AppException.class, () -> covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest(null)));
        Assertions.assertEquals(
                ExceptionManager.CovidUpdatesError.COUNTRY_ISO2_IS_MISSING.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw country iso2 is invalid exception")
    void shouldThrowCountryIso2IsInvalid() {

        AppException exception =
                assertThrows(AppException.class, () -> covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("INVALID")));
        Assertions.assertEquals(
                ExceptionManager.CovidUpdatesError.COUNTRY_ISO2_IS_INVALID.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw Could not get country wise covid info exception")
    void shouldThrowCouldNotGetCountryWiseCovidInfo() {

        AppException exception =
                assertThrows(AppException.class, () -> covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("XX")));
        Assertions.assertEquals(
                ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRY_WISE_COVID_INFO.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should get success response")
    void shouldNotThrowException() {

        List<CountryWiseCovidInfo> covidInfo = covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("NP"));
        assertNotNull(covidInfo);
        assertEquals(2, covidInfo.size());
        assertEquals( 20.0,  covidInfo.get(1).getConfirmedIncrementRate());
    }

    @Test
    @DisplayName("Should get correct confirm increase rate")
    void shouldGetCorrectConfirmedIncreaseRate() {

        List<CountryWiseCovidInfo> covidInfo = covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("NP"));
        assertNotNull(covidInfo);
        assertEquals(2, covidInfo.size());
        assertEquals( 20.0,  covidInfo.get(1).getConfirmedIncrementRate());
    }

    @Test
    @DisplayName("Should get correct confirm death rate")
    void shouldGetCorrectDeathRate() {

        List<CountryWiseCovidInfo> covidInfo = covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("NP"));
        assertNotNull(covidInfo);
        assertEquals( 0,  covidInfo.get(1).getDeathsRate());
    }

    @Test
    @DisplayName("Should get correct confirm recovery rate")
    void shouldGetCorrectRecoveryRate() {

        List<CountryWiseCovidInfo> covidInfo = covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("NP"));
        assertNotNull(covidInfo);
        assertEquals( 75,  covidInfo.get(1).getRecoveredRate());
    }

    @Test
    @DisplayName("Should get correct confirm active rate")
    void shouldGetCorrectActiveRate() {

        List<CountryWiseCovidInfo> covidInfo = covidUpdatesRepository.getCountryWiseCovidInfo(new GetCountryWiseCovidInfoRequest("NP"));
        assertNotNull(covidInfo);
        assertEquals( 25,  covidInfo.get(1).getActiveRate());
    }


}
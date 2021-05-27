package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.repositories.CovidUpdatesRepository;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.dtos.CountryInfo;
import global.sunil.covidupdates.repositories.services.Covid19APIService;
import global.sunil.covidupdates.repositories.services.domains.ServiceCountryInfo;
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
class CovidUpdateRepositoryImplGetCountriesTest {

    CovidUpdatesRepository covidUpdatesRepository;
    CovidUpdatesRepository covidUpdatesRepositoryWithInvalidCountries;
    @BeforeEach
    void setup(){
        Covid19APIService covid19APIService = Mockito.mock(Covid19APIService.class);
        Mockito.when(covid19APIService.getCountries()).thenReturn(Arrays.asList(new ServiceCountryInfo("Nepal", "NP") ));
        covidUpdatesRepository = new CovidUpdateRepositoryImpl(covid19APIService);

        Covid19APIService covid19APIServiceWithInvalidResponse = Mockito.mock(Covid19APIService.class);
        Mockito.when(covid19APIServiceWithInvalidResponse.getCountries()).thenThrow(
                new AppException(ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRIES));
        covidUpdatesRepositoryWithInvalidCountries = new CovidUpdateRepositoryImpl(covid19APIServiceWithInvalidResponse);


    }

    @Test
    @DisplayName("Should throw Could not get countries. exception")
    void shouldThrowCouldNotGetCountries() {

        AppException exception =
                assertThrows(AppException.class, () -> covidUpdatesRepositoryWithInvalidCountries.getCountries());
        Assertions.assertEquals(
                ExceptionManager.CovidUpdatesError.COULD_NOT_GET_COUNTRIES.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should get success response")
    void shouldWorkWell() {

        List<CountryInfo> countries = covidUpdatesRepository.getCountries();
        assertNotNull(countries);
        assertEquals(1, countries.size());
    }



}
package global.sunil.covidupdates.repositories.services.domains;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-27 - १३:४१
 */
public class ServiceCountryWiseCovidInfo {
    
    String ID;
    String Country;
    String CountryCode;
//    String Province;
//    String City;
//    String CityCode;
//    String Lat;
//    String Lon;
    int Confirmed;
    int Deaths;
    int Recovered;
    int Active;
    String Date;

    public ServiceCountryWiseCovidInfo(String ID, String country, String countryCode, int confirmed, int deaths, int recovered, int active, String date) {
        this.ID = ID;
        Country = country;
        CountryCode = countryCode;
        Confirmed = confirmed;
        Deaths = deaths;
        Recovered = recovered;
        Active = active;
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public int getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(int confirmed) {
        Confirmed = confirmed;
    }

    public int getDeaths() {
        return Deaths;
    }

    public void setDeaths(int deaths) {
        Deaths = deaths;
    }

    public int getRecovered() {
        return Recovered;
    }

    public void setRecovered(int recovered) {
        Recovered = recovered;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceCountryWiseCovidInfo that = (ServiceCountryWiseCovidInfo) o;
        return Confirmed == that.Confirmed && Deaths == that.Deaths && Recovered == that.Recovered && Active == that.Active && Objects.equals(ID, that.ID) && Objects.equals(Country, that.Country) && Objects.equals(CountryCode, that.CountryCode) && Objects.equals(Date, that.Date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Country, CountryCode, Confirmed, Deaths, Recovered, Active, Date);
    }
}

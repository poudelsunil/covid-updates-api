package global.sunil.covidupdates.repositories.dtos;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-25 - १५:३९
 */
public class CountryWiseCovidInfo {

    String countryName;
    int confirmed;
    int deaths;
    int recovered;
    int active;

    double confirmedIncrementRate;
    double deathsRate;
    double recoveredRate;
    double activeRate;

    String Date;

    public CountryWiseCovidInfo(String countryName, int confirmed, int deaths, int recovered, int active, String date) {
        this.countryName = countryName;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        Date = date;
    }

    public CountryWiseCovidInfo(String countryName, int confirmed, int deaths, int recovered,
                                int active, double confirmedIncrementRate,
                                double deathsRate, double recoveredRate,
                                double activeRate, String date) {
        this.countryName = countryName;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.confirmedIncrementRate = confirmedIncrementRate;
        this.deathsRate = deathsRate;
        this.recoveredRate = recoveredRate;
        this.activeRate = activeRate;
        Date = date;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public double getConfirmedIncrementRate() {
        return confirmedIncrementRate;
    }

    public void setConfirmedIncrementRate(double confirmedIncrementRate) {
        this.confirmedIncrementRate = confirmedIncrementRate;
    }

    public double getDeathsRate() {
        return deathsRate;
    }

    public void setDeathsRate(double deathsRate) {
        this.deathsRate = deathsRate;
    }

    public double getRecoveredRate() {
        return recoveredRate;
    }

    public void setRecoveredRate(double recoveredRate) {
        this.recoveredRate = recoveredRate;
    }

    public double getActiveRate() {
        return activeRate;
    }

    public void setActiveRate(double activeRate) {
        this.activeRate = activeRate;
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
        CountryWiseCovidInfo that = (CountryWiseCovidInfo) o;
        return confirmed == that.confirmed && deaths == that.deaths && recovered == that.recovered && active == that.active && Double.compare(that.confirmedIncrementRate, confirmedIncrementRate) == 0 && Double.compare(that.deathsRate, deathsRate) == 0 && Double.compare(that.recoveredRate, recoveredRate) == 0 && Double.compare(that.activeRate, activeRate) == 0 && Objects.equals(countryName, that.countryName) && Objects.equals(Date, that.Date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, confirmed, deaths, recovered, active, confirmedIncrementRate, deathsRate, recoveredRate, activeRate, Date);
    }
}

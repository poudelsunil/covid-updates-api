package global.sunil.covidupdates.repositories.services.domains;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-25 - १६:४८
 */
public class ServiceCountryInfo {
    String Country;
//    String Slug;
    String ISO2;

    public ServiceCountryInfo(String country, String ISO2) {
        Country = country;
        this.ISO2 = ISO2;
    }

//    public ServiceCountryInfo(String country, String slug, String ISO2) {
//        Country = country;
//        Slug = slug;
//        this.ISO2 = ISO2;
//    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

//    public String getSlug() {
//        return Slug;
//    }
//
//    public void setSlug(String slug) {
//        Slug = slug;
//    }

    public String getISO2() {
        return ISO2;
    }

    public void setISO2(String ISO2) {
        this.ISO2 = ISO2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceCountryInfo that = (ServiceCountryInfo) o;
        return Objects.equals(Country, that.Country) /*&& Objects.equals(Slug, that.Slug)*/ && Objects.equals(ISO2, that.ISO2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Country, /*Slug,*/ ISO2);
    }
}

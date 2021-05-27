package global.sunil.covidupdates.repositories.dtos;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-25 - १५:३९
 */
public class CountryInfo {

    String name;
    String iso2;

    public CountryInfo(String name, String iso2) {
        this.name = name;
        this.iso2 = iso2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    @Override
    public String toString() {
        return "CountryInfo{" +
                "name='" + name + '\'' +
                ", iso2='" + iso2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryInfo that = (CountryInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(iso2, that.iso2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iso2);
    }
}

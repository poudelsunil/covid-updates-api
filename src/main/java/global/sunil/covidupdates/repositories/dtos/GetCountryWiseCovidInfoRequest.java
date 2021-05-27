package global.sunil.covidupdates.repositories.dtos;

import global.sunil.covidupdates.lib.services.ServiceObject;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-27 - १४:४१
 */
public class GetCountryWiseCovidInfoRequest implements ServiceObject {
    String iso2;

    public GetCountryWiseCovidInfoRequest() {
    }

    public GetCountryWiseCovidInfoRequest(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetCountryWiseCovidInfoRequest that = (GetCountryWiseCovidInfoRequest) o;
        return Objects.equals(iso2, that.iso2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso2);
    }
}

package global.sunil.covidupdates.rest.adaptors;

import global.sunil.covidupdates.lib.services.RequestAdaptor;
import global.sunil.covidupdates.repositories.dtos.GetCountryWiseCovidInfoRequest;

import javax.json.JsonObject;

/**
 * @author Sunil on 2021-05-25 - १५:३०
 */
public class GetCountryWiseCovidInfoRequestAdaptor implements RequestAdaptor<GetCountryWiseCovidInfoRequest> {
    @Override
    public GetCountryWiseCovidInfoRequest toServiceObject(JsonObject jsonObject) {

        GetCountryWiseCovidInfoRequest request = new GetCountryWiseCovidInfoRequest();
        return request;
    }
}

package global.sunil.covidupdates.rest.adaptors;

import global.sunil.covidupdates.lib.utils.RequestAdaptor;
import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonObject;

/**
 * @author Sunil on 2021-05-25 - १५:३०
 */
@Named
public class SendEmailRequestAdaptor implements RequestAdaptor<SendEmailRequest> {

    @Override
    public SendEmailRequest toServiceObject(JsonObject jsonObject) {
        SendEmailRequest request = new SendEmailRequest();
        request.setSubject(jsonObject.getString("subject"));
        request.setMessage(jsonObject.getString("content"));
        request.setCount(jsonObject.getInt("count", 50));
        return request;
    }
}

package global.sunil.covidupdates.lib.services;

import global.sunil.covidupdates.lib.services.ServiceObject;

import javax.json.JsonObject;

/**
 * @author Sunil on 2021-05-25 - १५:३१
 */

@FunctionalInterface
public interface RequestAdaptor<T extends ServiceObject> {
    T toServiceObject(JsonObject jsonObject);
}

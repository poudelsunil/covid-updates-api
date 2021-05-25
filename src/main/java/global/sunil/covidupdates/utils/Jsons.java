package global.sunil.covidupdates.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.*;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Sunil on 2021-05-25 - १४:०१
 */
public class Jsons {

    private static Gson gson = new GsonBuilder().serializeNulls().create();

    private Jsons() {}

    /**
     * To Json EntityConverter using Goolge's Gson Package<br>
     * this method converts a simple object to a json string<br>
     *
     * @param obj
     * @return a json string
     */
    public static <T> String toJsonObj(T obj) {
        return gson.toJson(obj);
    }

    /**
     * Converts a collection of objects using Google's Gson Package
     *
     * @param objCol
     * @return a json string array
     */
    public static <T> String toJsonList(List<T> objCol) {
        return gson.toJson(objCol);
    }

    /**
     * Returns the specific object given the Json String
     *
     * @param <T>
     * @param jsonString
     * @param obj
     * @return a specific object as defined by the user calling the method
     */
    public static <T> T fromJsonToObj(String jsonString, Class<T> obj) {
        if (!isValidJsonString(jsonString)) {
            throw new IllegalArgumentException(" Invalid Json data.");
        }
        return gson.fromJson(jsonString, obj);
    }

    /**
     * Returns a list of specified object from the given json array
     *
     * @param <T>
     * @param jsonString
     * @param t the type defined by the user
     * @return a list of specified objects as given in the json array
     */
    public static <T> List<T> fromJsonToList(String jsonString, Type t) {
        if (!isValidJsonString(jsonString)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(gson.fromJson(jsonString, t));
    }

    public static String toJsonTree(Object src) {
        return gson.toJsonTree(src).toString();
    }

    public static JsonObject toJsonObject(String jsonData) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonData))) {
            return jsonReader.readObject();
        }
    }

    public static double asDouble(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        JsonNumber jsonValue = jsonObject.getJsonNumber(key);
        if (jsonValue == null) {
            return 0;
        }
        return jsonValue.doubleValue();
    }

    public static long asLong(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        JsonNumber jsonValue = jsonObject.getJsonNumber(key);
        if (jsonValue == null) {
            return 0;
        }
        return jsonValue.longValue();
    }

    public static int asInt(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        JsonNumber jsonValue = jsonObject.getJsonNumber(key);
        if (jsonValue == null) {
            return 0;
        }
        return jsonValue.intValue();
    }

    public static String asString(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        JsonString jsonString = jsonObject.getJsonString(key);
        if (jsonString == null) {
            return null;
        }
        return jsonString.getString();
    }

    /*
    public static Optional<String> asDateTimeFormat(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null)
            return Optional.empty();
        return Optional.of(AppConfig.getDateFormatter().format(temporalAccessor));
    }
     */
    public static boolean isValidJsonString(String jsonString) {
        if (HelperUtils.isBlankOrNull(jsonString)) {
            return false;
        }
        try {
            gson.fromJson(jsonString, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

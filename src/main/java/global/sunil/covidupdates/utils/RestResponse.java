package global.sunil.covidupdates.utils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;
import java.io.StringReader;

/**
 * @author Sunil on 2021-05-25 - १४:००
 */
public class RestResponse {

    private String code;
    private String message;
    private Object data;

    public RestResponse() {}

    public RestResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response ok(Object data) {
        return Response.ok((new RestResponse("0", "SUCCESS", data)).toJson())
//        .status(200)
//        .header("Access-Control-Allow-Origin", "*")
//        .header("Access-Control-Allow-Credentials", "true")
//        .header("Access-Control-Allow-Headers",
//            "origin, content-type, accept, authorization")
//        .header("Access-Control-Allow-Methods",
//            "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    public static Response ok() {
        return Response.ok((new RestResponse("0", "SUCCESS", null)).toJson()).build();
    }

    public static Response errorResponse(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity((new RestResponse("500", message, null)).toJson())
                .build();
    }

    public static RestResponse error(String message) {
        return new RestResponse("400", message, null);
    }

    public static RestResponse error(String code, String message) {
        return new RestResponse(code, message, null);
    }

    public static Response okWithJsonObject(JsonArrayBuilder data) {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("code", "0")
                        .add("message", "SUCCESS")
                        .add("data", data)
                        .build();
        return Response.ok(jsonObject).build();
    }

    public static Response okWithJsonObject(JsonObject data) {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("code", "0")
                        .add("message", "SUCCESS")
                        .add("data", data)
                        .build();
        return Response.ok(jsonObject).build();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonObject toJson() {
        JsonObject object = null;
        JsonValue value = null;
        if (this.data != null) {
            value = Json.createReader(new StringReader(Jsons.toJsonObj(this.data))).read();
            return Json.createObjectBuilder()
                    .add("code", this.code)
                    .add("message", this.message)
                    .add("data", value)
                    .build();
        }

        return Json.createObjectBuilder().add("code", this.code).add("message", this.message).build();
    }
}

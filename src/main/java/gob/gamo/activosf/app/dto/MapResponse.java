package gob.gamo.activosf.app.dto;

import java.util.HashMap;

public class MapResponse extends HashMap<String, Object> {
    public MapResponse() {
        super.put("success", true);
        super.put("msg", "");
        super.put("data", null);
    }

    public MapResponse(boolean success) {
        super.put("success", success);
    }

    public MapResponse(String msg) {
        super.put("msg", msg);
    }

    public MapResponse(Object data) {
        super.put("data", data);
    }

    public MapResponse(boolean success, String msg) {
        super.put("success", success);
        super.put("msg", msg);
    }

    public MapResponse(boolean success, Object data) {
        super.put("success", success);
        super.put("data", data);
    }

    public MapResponse(boolean success, String msg, Object data) {
        super.put("success", success);
        super.put("msg", msg);
        super.put("data", data);
    }

    public static MapResponse success() {
        return new MapResponse(true);
    }

    public static MapResponse success(String msg) {
        return new MapResponse(true, msg);
    }

    public static MapResponse success(Object data) {
        return new MapResponse(true, data);
    }

    public static MapResponse failure() {
        return new MapResponse(false);
    }

    public static MapResponse failure(String msg) {
        return new MapResponse(false, msg);
    }

    public static MapResponse failure(Object msg) {
        return new MapResponse(false, msg);
    }
}

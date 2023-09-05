package gob.gamo.activosf.app.commons;

public interface Constants {
    String API_URL_ROOT = "/api";
    String API_URL_VERSION = "/v1";
    String API_URL_CLASS_UNIDS = "/unidades";

    String API_UNIDS = "/unidades";
    String API_UNIDS_SLUG = API_UNIDS + "/{slug}";
    String API_UNIDS_SLUG_EMPLS = API_UNIDS_SLUG + "/empleados";
    String API_UNIDS_SLUG_EMPLS_IDEMPL = API_UNIDS_SLUG_EMPLS + "/{id}}";

    public static final Integer SEC_MAX_ATTEMPT = 3;
    public static final Integer SEC_TIME_LOCKED_FOR_ATTEMPS = 5 * 60; // minutos
    public static final Integer SEC_TIME_LIFE_FOR_SESSIONS_REST = 5; // minutos
    public static final Integer SEC_TIME_LIFE_BEFORE_TIMEOUT = 15; // secs
    public static final String SEC_HEADER_TOKEN_REFRESH = "artaf";
}

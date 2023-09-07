package gob.gamo.activosf.app.commons;

public interface Constants {
    public static final String BASE_DIR_APP = "/opt/aplicaciones/activosf/";
    public static final String FILE_APPLICATION_WEB = "applicationw.properties";
    public static final String FILE_APPLICATION = "application.properties";

    String API_URL_ROOT = "/api";
    String API_URL_VERSION = "/v1";
    String API_URL_CLASS_UNIDS = "/unidades";

    String API_UNIDS = "/unidades";
    String API_UNIDS_SLUG = API_UNIDS + "/{slug}";
    String API_UNIDS_SLUG_EMPLS = API_UNIDS_SLUG + "/empleados";
    String API_UNIDS_SLUG_EMPLS_IDEMPL = API_UNIDS_SLUG_EMPLS + "/{id}}";

    String API_MATERIALES = "/materiales";
    String API_ACTIVOSF = "/activosf";
    String API_ALMACENES = "/almacenes";    
    String API_CODIGOSCONTABLES = "/codigosc";        
    String API_FAMILIASACTIVOS = "/familiasaf";        
    String API_KARDEX = "/kardex";            
    String API_PARTIDAS = "/partidas";            
    String API_PROVEEDORES = "/proveedores";            
    String API_EMPLEADOS = "/empleados";                
    String API_PERSONAS = "/personas";                    

    public static final Integer SEC_MAX_ATTEMPT = 3;
    public static final Integer SEC_TIME_LOCKED_FOR_ATTEMPS = 5 * 60; // minutos
    public static final Integer SEC_TIME_LIFE_FOR_SESSIONS_REST = 5; // minutos
    public static final Integer SEC_TIME_LIFE_BEFORE_TIMEOUT = 15; // secs
    public static final String SEC_HEADER_TOKEN_REFRESH = "artaf";
}

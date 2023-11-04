package gob.gamo.activosf.app.commons;

public interface Constants {
    public static final String BASE_DIR_APP = "/opt/aplicaciones/activosf/";
    public static final String FILE_APPLICATION_WEB = "applicationw.properties";
    public static final String FILE_APPLICATION = "application.properties";

    String API_URL_ROOT = "/api";
    String API_URL_VERSION = "/v1";
    String API_URL_CLASS_UNIDS = "/unidades";
    String API_ROOT_VERSION = API_URL_ROOT + API_URL_VERSION;

    String API_PUBLIC = "/auth";
    String API_LOGIN = API_PUBLIC + "/login";

    String API_TABLAS = "/tablas";
    String API_TABLASDET = "/tablasdet";
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
    String API_ITEMS = "/items";    
    String API_TRANSACCION = "/trx";        
    String API_TRANSDET = "/trxdet";        
    String API_PREPARE = "/pre";        

    String API_USUARIOS = "/users";
    String API_ROLES = "/roles";
    String API_PERMISOS = "/permisos";

    String REC_TABLAS = "TABLAS";
    String REC_UNIDS = "UNIDADES";
    String REC_UNIDS_SLUG = REC_UNIDS;
    String REC_UNIDS_SLUG_EMPLS = REC_UNIDS_SLUG + "EMPLEADOS";
    String REC_UNIDS_SLUG_EMPLS_IDEMPL = REC_UNIDS_SLUG_EMPLS;
    String REC_MATERIALES = "MATERIALES";
    String REC_ACTIVOSF = "ACTIVOSF";
    String REC_ALMACENES = "ALMACENES";
    String REC_CODIGOSCONTABLES = "CODIGOSC";
    String REC_FAMILIASACTIVOS = "FAMILIASAF";
    String REC_KARDEX = "KARDEX";
    String REC_PARTIDAS = "PARTIDAS";
    String REC_PROVEEDORES = "PROVEEDORES";
    String REC_EMPLEADOS = "EMPLEADOS";
    String REC_PERSONAS = "PERSONAS";
    String REC_USUARIOS = "USERS";
    String REC_ROLES = "ROLES";
    String REC_PERMISOS = "PERMISOS";
    String REC_ITEMS = "ITEMS";
    String REC_TRANSACCION = "TRX";    
    String REC_TRANSDET = "TRXDET";        

    public static final Integer SEC_MAX_ATTEMPT = 3;
    public static final Integer SEC_TIME_LOCKED_FOR_ATTEMPS = 5 * 60; // minutos
    public static final Integer SEC_TIME_LIFE_FOR_SESSIONS_REST = 5; // minutos
    public static final Integer SEC_TIME_LIFE_BEFORE_TIMEOUT = 15; // secs
    public static final String SEC_HEADER_TOKEN_REFRESH = "artaf";

    String HEADER_X_ACTIVOS = "X-Activosf";

    static Integer TAB_TIPOPERS_NATURAL = 1;

    static final Integer TAB_MD = 1;
    static final Integer TAB_MD_AD = 1; //ADMINIS
    static final Integer TAB_MD_SG = 2; //seguridad
    static final Integer TAB_MD_AF = 5;
    static final Integer TAB_MD_AL = 8;

    static final Integer TAB_AF = TAB_MD_AF;
    static final Integer TAB_AF_CREAKARDEX = 1;
        
    static final Integer TAB_ALM = TAB_MD_AL;    
    static final Integer TAB_ALM_CREKARDEX = 1;
    static final Integer TAB_ALM_INGRESO = 2;
    static final Integer TAB_ALM_SALIDA = 3;
    static final Integer TAB_ALM_ACTKRD = 5;

    static final Integer TAB_TOPSUB = 5;
    static final Integer TAB_TOPSUB_IO = 2;

    static final Integer TAB_MY = 58;    
    static final Integer TAB_MY_SALDOINICIAL = 1;        
    static final Integer TAB_MY_SALIDA_STOCK = 2;            
    static final Integer TAB_MY_INGRESO_STOCK = 3;

    static final Integer TAB_TASK = 30;
    static final Integer TAB_TASK_PRE = 1;
    static final Integer TAB_TASK_VER = 2;
    static final Integer TAB_TASK_REG = 3;
    static final Integer TAB_TASK_ACT = 5;
    static final Integer TAB_TASK_DEL = 6;

    static final Integer TAB_UND = 32;    
    static final Integer TAB_UND_UND = 1;    
}

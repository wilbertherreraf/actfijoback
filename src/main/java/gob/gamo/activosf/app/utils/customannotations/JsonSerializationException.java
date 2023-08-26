package gob.gamo.activosf.app.utils.customannotations;

public class JsonSerializationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JsonSerializationException(String message) {
        super(message);
    }
}

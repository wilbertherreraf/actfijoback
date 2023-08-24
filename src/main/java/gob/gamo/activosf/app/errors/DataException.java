package gob.gamo.activosf.app.errors;

public class DataException extends RuntimeException {

    public DataException() {
        super();
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}

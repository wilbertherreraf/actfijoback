package gob.gamo.activosf.app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
public class NotHavePermissionException extends RuntimeException {

    public NotHavePermissionException(String message) {
        super(message);
    }
}

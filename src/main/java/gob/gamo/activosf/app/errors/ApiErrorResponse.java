package gob.gamo.activosf.app.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

// @Builder
public class ApiErrorResponse {
    private String errorCode = "00000";
    private String message;
    private Integer status;
    private String path;
    private Map<String, String> properties;
    private List<String> errors;
    /*     public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    } */

    public ApiErrorResponse(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.status = status.value();
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status.value();
        this.message = message;
        errors = Arrays.asList(error);
    }

    public void setError(String error) {
        if (errors == null) errors = new ArrayList<>();
        errors.add(error);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public Map<String, String> getProperties() {
        if (properties == null) properties = new HashMap<>();
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

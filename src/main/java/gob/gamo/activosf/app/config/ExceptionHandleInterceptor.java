package gob.gamo.activosf.app.config;

import java.sql.SQLException;
import java.util.Date;
import java.util.NoSuchElementException;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.errors.NotHavePermissionException;
import gob.gamo.activosf.app.errors.TokenRefreshException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleInterceptor extends ResponseEntityExceptionHandler {

    /*
     * @ExceptionHandler(MethodArgumentNotValidException.class)
     * public ResponseEntity<Object>
     * handleValidationException(MethodArgumentNotValidException e) {
     * log.error("Error Interceptor:> " + e.getMessage());
     * Map<String, String> validationErrors = new HashMap<>();
     * e.getBindingResult().getFieldErrors()
     * .forEach(fieldError -> validationErrors.put(fieldError.getField(),
     * fieldError.getDefaultMessage()));
     *
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.error(
     * validationErrors));
     * }
     */

    @ExceptionHandler(SQLException.class)
    public ProblemDetail handle(SQLException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Error de Base de datos: " + e.getMessage());
        return pd;
    }

    /*
     * @ExceptionHandler(MethodArgumentNotValidException.class)
     * public ProblemDetail handle(MethodArgumentNotValidException e) {
     * log.error("Error Interceptor:> " + e.getMessage());
     * ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
     * e.getMessage());
     * e.getBindingResult().getFieldErrors()
     * .forEach(fieldError -> pd.setProperty(fieldError.getField(),
     * fieldError.getDefaultMessage()));
     * return pd;
     * }
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handle(IllegalArgumentException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handle(NoSuchElementException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handle(AccessDeniedException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, e.getMessage() + " or you don't have permission to perform this operation!");
    }

    @ExceptionHandler(NotHavePermissionException.class)
    public ProblemDetail handle(NotHavePermissionException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, e.getMessage());
    }

    @ExceptionHandler(JwtValidationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handle(JwtValidationException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handle(AuthenticationException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
    @ExceptionHandler(TokenRefreshException.class)
    public ProblemDetail handle(TokenRefreshException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }
    
    @ExceptionHandler(DataException.class)
    protected ProblemDetail handleCustomError(RuntimeException e) {
        log.error("Error Interceptor:> " + e.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        log.error("Error Interceptor:> Error interno: " + e.getMessage());
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno: " + e.getMessage());
        for (Throwable cause = e; (cause != null && cause != cause.getCause()); cause = cause.getCause()) {
            if (cause instanceof SQLException) {
                SQLException ex = (SQLException) cause;
                pd.setDetail(cause.getMessage());
                pd.setProperty("Error Code", ex.getErrorCode());
                pd.setProperty("StackTrace", ex.getStackTrace());
            }
        }
        return pd;
    }

    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleCustomError(Throwable e) {
        log.error("Error Interceptor:> Error interno: " + e.getMessage());
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno: " + e.getMessage());
        for (Throwable cause = e; (cause != null && cause != cause.getCause()); cause = cause.getCause()) {
            if (cause instanceof SQLException) {
                SQLException ex = (SQLException) cause;
                pd.setDetail(cause.getMessage());
                pd.setProperty("Error Code", ex.getErrorCode());
                pd.setProperty("StackTrace", ex.getStackTrace());
            }
        }
        return pd;
    }
}

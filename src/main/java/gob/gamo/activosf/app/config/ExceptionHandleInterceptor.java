package gob.gamo.activosf.app.config;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.errors.DataException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handle(IllegalArgumentException e) {
        log.error("Error:> " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handle(NoSuchElementException e) {
        log.error("Error:> " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handle(AccessDeniedException e) {
        log.error("Error:> " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handle(AuthenticationException e) {
        log.error("Error:> " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    /* @ExceptionHandler(unauthori.class)
    public ProblemDetail handle(HttpRequestMethodNotSupportedException e) {
        log.error("Error:> " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    } */

    @ExceptionHandler(DataException.class)
    protected ProblemDetail handleCustomError(RuntimeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception e) {
        log.error("Please contact the administrator. An unknown error occurred: " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An error has occurred. Please contact the administrator.");
    }

    @ExceptionHandler(Throwable.class)
    protected ProblemDetail handleCustomError(Throwable ex) {
        log.error("Please contact the administrator. An unknown error occurred: " + ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Please contact the administrator. An unknown error occurred: " + ex.getMessage());
    }
}

package gob.gamo.activosf.app.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.errors.CustomException;
import io.jsonwebtoken.ExpiredJwtException;

@Slf4j
@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<CustomException> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        log.info("en ResponseEntity");
        CustomException expiredJwtException = new CustomException(
                ex.getClass().toString(),
                ex.getMessage() + " or you don't have permission to perform this operation!",
                request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(expiredJwtException, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("en handleMethodArgumentNotValid");
        CustomException badRequest = new CustomException(
                ex.getClass().toString(), ex.getMessage(), request.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<CustomException> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {

        log.info("en handleAccessDeniedException");
        CustomException accessDeniedException = new CustomException(
                ex.getClass().toString(),
                ex.getMessage() + " or you don't have permission to perform this operation!",
                request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(accessDeniedException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<CustomException> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        log.info("en handleBadCredentialsException");
        CustomException badCredentialsException = new CustomException(
                ex.getClass().toString(), ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(badCredentialsException, HttpStatus.BAD_REQUEST);
    }
}

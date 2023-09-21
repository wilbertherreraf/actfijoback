package gob.gamo.activosf.app.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.errors.ApiErrorResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.errors.TokenRefreshException;
import gob.gamo.activosf.app.utils.WebUtil;

// @ControllerAdvice
@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.valueOf(apiError.getStatus()), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            final TypeMismatchException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error =
                ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            final MissingServletRequestPartException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getRequestPartName() + " part is missing";
        final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getParameterName() + " parameter is missing";
        final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    //

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            final ConstraintViolationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(),
                builder.substring(0, builder.length() - 2));
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({JwtValidationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> unauthorized(final JwtValidationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    //    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> accessDenied(final AccessDeniedException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        apiError.setMessage("Acceso denegado, no tiene permisos para acceder al recurso");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handle(final TokenRefreshException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), "error occurred");
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Object> handle(final DataException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), "error occurred");
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({SQLException.class, HibernateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handle(final SQLException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        for (Throwable cause = ex; (cause != null && cause != cause.getCause()); cause = cause.getCause()) {
            if (cause instanceof SQLException) {
                SQLException e = (SQLException) cause;
                apiError.setMessage(cause.getMessage());
                apiError.setErrorCode(String.valueOf(e.getErrorCode()));
                // apiError.setError("Database exception");
            }
        }
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        //
        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        for (Throwable cause = ex; (cause != null && cause != cause.getCause()); cause = cause.getCause()) {
            if (cause instanceof SQLException) {
                SQLException e = (SQLException) cause;
                apiError.setMessage(cause.getMessage());
                apiError.setErrorCode(String.valueOf(e.getErrorCode()));
                // apiError.setError("Database exception");
            }
        }
        apiError.setPath(WebUtil.getRequest().getRequestURI());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

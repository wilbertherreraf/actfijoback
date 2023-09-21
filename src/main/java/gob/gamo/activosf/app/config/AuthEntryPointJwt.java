package gob.gamo.activosf.app.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.errors.ApiErrorResponse;
import gob.gamo.activosf.app.errors.NotHavePermissionException;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        log.error("Unauthorized error: {} url: {}", authException.getMessage(), request.getRequestURL());

        final ApiErrorResponse apiError =
                new ApiErrorResponse(HttpStatus.UNAUTHORIZED, authException.getLocalizedMessage(), "error occurred");

        apiError.setPath(request.getRequestURL().toString());

        if (request.getRequestURL().toString().endsWith("/error")) {
            apiError.setPath(Constants.API_ROOT_VERSION + "/error");
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Exception exception = (Exception) request.getAttribute("exception");
        if (exception != null) {
            if (exception instanceof NotHavePermissionException) {
                apiError.setPath(Constants.API_ROOT_VERSION + Constants.API_USUARIOS + "/refreshtoken");
                apiError.setStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
                apiError.setMessage(exception.getLocalizedMessage());
            } else {
                apiError.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED));
                apiError.setPath(Constants.API_ROOT_VERSION + Constants.API_USUARIOS + "/loggout");
                apiError.setMessage(exception.getLocalizedMessage());
            }
        } else {
            if (authException.getCause() != null) {
                apiError.setError(authException.getCause().toString());
            }
        }
        Gson gson = new Gson();
        String body = gson.toJson(apiError);
        log.info("commmence {}", body);
        response.setStatus(apiError.getStatus());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), apiError);
    }
}

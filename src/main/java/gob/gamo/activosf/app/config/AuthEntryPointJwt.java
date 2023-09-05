package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import gob.gamo.activosf.app.errors.NotHavePermissionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        final Map<String, Object> body = new HashMap<>();
        body.put("path", request.getRequestURL());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Exception exception = (Exception) request.getAttribute("exception");
        String messageErr = "";
        String message = "";
        if (exception != null) {
            messageErr = exception.toString();
            if (exception instanceof NotHavePermissionException) {
                response.setStatus(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED);
                body.put("path", "/api/user/refreshtoken");
            } else {
                body.put("path", "/api/user/loggout");
            }
            body.put("message", exception.getMessage());
        } else {
            body.put("message", authException.getMessage());
            if (authException.getCause() != null) {
                message = authException.getCause().toString() + " " + authException.getMessage();
            } else {
                message = authException.getMessage();
            }
        }

        body.put("status", response.getStatus());
        body.put("error", "Unauthorized");
        body.put("cause", message);

        log.info("commmence {}", body.toString());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}

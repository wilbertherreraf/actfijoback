package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientErrorLoggingFilter extends GenericFilterBean {
    private List<HttpStatus> errorCodes;

    public ClientErrorLoggingFilter(List<HttpStatus> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("en  filter logs ");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            log.info("en  filter auth null ");
            chain.doFilter(request, response);
            return;
        }
        int status = ((HttpServletResponse) response).getStatus();
        log.info("en  filter auth status {}", status);
        if (status < 400 || status >= 500) {
            chain.doFilter(request, response);

            return;
        }

        if (errorCodes == null) {
            log.info("User " + auth.getName() + " encountered error " + status);
        } else {
            if (errorCodes.stream().anyMatch(s -> s.value() == status)) {
                log.info("User " + auth.getName() + " encountered error " + status);
            }
        }
        log.info("en  filter logs saliendo ...");
        chain.doFilter(request, response);
    }
}
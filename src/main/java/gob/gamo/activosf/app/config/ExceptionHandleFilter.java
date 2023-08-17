package gob.gamo.activosf.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandleFilter extends OncePerRequestFilter {
    private final ExceptionHandleInterceptor exceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            log.info(request.getRequestURL().toString(), request.getRequestedSessionId());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }
}

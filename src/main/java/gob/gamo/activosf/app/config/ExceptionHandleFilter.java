package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.handlers.RequestWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandleFilter extends OncePerRequestFilter {
    private final ExceptionHandleInterceptor exceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            log.info(
                    "DoFilter: {} -> {} {}",
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getHeaderNames(),
                    request.getContentLength());
            log.info("DoFilter: {}", request.getContentType());
            /*             if (request.getContentLength() > 0 && request.getMethod().equals("PUT")) {
                byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
                String content = new String(body, "UTF-8");
                log.info("Content ", content, body.length);
            } */
            RequestWrapper cachedHttpServletRequest = new RequestWrapper(request);
            // printReq(request);
            log.info("REQUEST DATA: "
                    + IOUtils.toString(cachedHttpServletRequest.getInputStream(), StandardCharsets.UTF_8));

            filterChain.doFilter(cachedHttpServletRequest, response);
        } catch (Exception e) {
            log.error("Error en filter " + e.getMessage(), e);
            exceptionHandler.handle(e);
        } finally {

            log.info("Finally filter ... {}", response.getStatus(), response.getHeaderNames());
        }
    }

    private void printReq(HttpServletRequest request) throws IOException {
        // Wrap the request
        RequestWrapper wrapper = new RequestWrapper(request);

        // Get the input stream from the wrapper and convert it into byte array
        byte[] body = StreamUtils.copyToByteArray(wrapper.getInputStream());

        // use jackson ObjectMapper to convert the byte array to Map (represents JSON)
        String jsonRequest = new ObjectMapper().writeValueAsString(body);
        log.info("Map request", jsonRequest);
    }
}

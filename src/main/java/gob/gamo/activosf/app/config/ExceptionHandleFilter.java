package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.errors.NotHavePermissionException;
import gob.gamo.activosf.app.handlers.RequestWrapper;
import gob.gamo.activosf.app.security.SessionsSearcherService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandleFilter extends OncePerRequestFilter {
    private static final Pattern AUTHORIZATION_PATTERN =
            Pattern.compile("^Token (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    private final JwtDecoder jwtDecoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionsSearcherService sessionsSearcherService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String authHdrToken = null;
        String refreshTk = null;
        String requestURL = request.getRequestURL().toString();
        RequestWrapper cachedHttpServletRequest = null;
        try {
            log.info(
                    "XXX:**=>DoFilter Inicio: ({}) {}-> {} rsaPublicKey null? {} jwtDecoder null? {}",
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getContentType(),
                    (jwtDecoder == null));

            /*        request.getParameterMap().entrySet().forEach(entry -> {
                log.info("parameter {} -> {}", entry.getKey(), entry.getValue());
            }); */
            cachedHttpServletRequest = new RequestWrapper(request);

            log.info("REQUEST DATA: "
                    + IOUtils.toString(cachedHttpServletRequest.getInputStream(), StandardCharsets.UTF_8));

            authHdrToken = resolveFromAuthorizationHeader(cachedHttpServletRequest);
            if (authHdrToken != null) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                JwtAuthenticationToken authToken =
                        (JwtAuthenticationToken) jwtTokenProvider.getAuthenticationJwt(authHdrToken);
                // boolean validTk = jwtTokenProvider.validateToken(authHdrToken);
                if (authToken != null && securityContext.getAuthentication() == null) {

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(cachedHttpServletRequest));
                    securityContext.setAuthentication(authToken);
                    refreshTk = jwtTokenProvider.getRefreshToken(authHdrToken);
                    response.setHeader(Constants.HEADER_X_ACTIVOS, refreshTk);
                    cachedHttpServletRequest.setAttribute(Constants.HEADER_X_ACTIVOS, refreshTk);
                    log.info("XXX:=>Authenticated user " + authToken.getName() + ", setting security context!"
                            + " session: " + refreshTk);
                }
            }
            log.info(
                    "XXX:=>DoFilter FINNNNN: ({}) {}-> {}",
                    cachedHttpServletRequest.getMethod(),
                    cachedHttpServletRequest.getRequestURL().toString(),
                    cachedHttpServletRequest.getContentType());

        } catch (JwtValidationException e) {
            log.error("Error en filter JwtValidationException " + e.getMessage(), e);

            refreshTk = jwtTokenProvider.getRefreshToken(authHdrToken);
            boolean existTk = sessionsSearcherService.existsSession(refreshTk);
            // allow for Refresh Token creation if following conditions are true.
            if (existTk && requestURL.toLowerCase().contains("refreshtoken")) {
                cachedHttpServletRequest.setAttribute(Constants.HEADER_X_ACTIVOS, refreshTk);
                response.setHeader(Constants.HEADER_X_ACTIVOS, refreshTk);
                allowForRefreshToken(cachedHttpServletRequest, refreshTk);
            } else {
                // PROXY_AUTHENTICATION_REQUIRED
                if (existTk) {
                    cachedHttpServletRequest.setAttribute(Constants.SEC_HEADER_TOKEN_REFRESH, existTk);
                    cachedHttpServletRequest.setAttribute(
                            "exception", new NotHavePermissionException("Require Refresh"));
                    cachedHttpServletRequest.setAttribute(Constants.HEADER_X_ACTIVOS, refreshTk);
                    response.setHeader(Constants.HEADER_X_ACTIVOS, refreshTk);
                } else cachedHttpServletRequest.setAttribute("exception", e);
            }
            // exceptionHandler.handle(e);
        }
        filterChain.doFilter(cachedHttpServletRequest, response);
    }

    private void allowForRefreshToken(HttpServletRequest request, String tkRefresh) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT

        request.setAttribute(Constants.SEC_HEADER_TOKEN_REFRESH, tkRefresh);
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorization, "token")) return null;

        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }
}

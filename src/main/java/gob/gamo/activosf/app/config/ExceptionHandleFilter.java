package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.errors.NotHavePermissionException;
import gob.gamo.activosf.app.security.SessionsSearcherService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandleFilter extends OncePerRequestFilter {
    private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^Token (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    private final ExceptionHandleInterceptor exceptionHandler;
    private final JwtDecoder jwtDecoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionsSearcherService sessionsSearcherService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String authHdrToken = null;
        try {
            log.info(
                    "XXX:**=>DoFilter Inicio: ({}) {}-> {} rsaPublicKey null? {} jwtDecoder null? {}",
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getContentType(),
                    (jwtDecoder == null));

            request.getParameterMap().entrySet().forEach(entry -> {
                log.info("parameter {} -> {}", entry.getKey(), entry.getValue());
            });
            // RequestWrapper cachedHttpServletRequest = new RequestWrapper(request);
            /*
             * log.info("REQUEST DATA: "
             * + IOUtils.toString(cachedHttpServletRequest.getInputStream(),
             * StandardCharsets.UTF_8));
             */

            authHdrToken = resolveFromAuthorizationHeader(request);
            if (authHdrToken != null) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                JwtAuthenticationToken authToken = (JwtAuthenticationToken) jwtTokenProvider
                        .getAuthenticationJwt(authHdrToken);
                //boolean validTk = jwtTokenProvider.validateToken(authHdrToken);
                if ( authToken != null && securityContext.getAuthentication() == null) {

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authToken);
                    log.info("XXX:=>Authenticated user " + authToken.getName() + ", setting security context!"
                            + " sixe grantes: ");
                } 
            }
            log.info(
                    "XXX:=>DoFilter FINNNNN: ({}) {}-> {}",
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getContentType());

        } catch (JwtValidationException e) {
            log.error("Error en filter JwtValidationException " + e.getMessage(), e);
            //String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            String refreshTk = jwtTokenProvider.getRefreshToken(authHdrToken);
            boolean existTk = sessionsSearcherService.existsSession(refreshTk);
            // allow for Refresh Token creation if following conditions are true.
            if (existTk && requestURL.toLowerCase().contains("refreshtoken")) {
                allowForRefreshToken(e, request,refreshTk);
            } else {
                // PROXY_AUTHENTICATION_REQUIRED
                if (existTk){
                    request.setAttribute(Constants.SEC_HEADER_TOKEN_REFRESH, existTk);
                    request.setAttribute("exception", new NotHavePermissionException("Require Refresh"));
                } else 
                    request.setAttribute("exception", e);
            }
            //exceptionHandler.handle(e);
        } finally {

            log.info(
                    "Finally filter ... {} Uri: {}",
                    response.getStatus(),
                    request.getRequestURL().toString());
        }
        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(JwtValidationException ex, HttpServletRequest request, String tkRefresh) {
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT

        request.setAttribute(Constants.SEC_HEADER_TOKEN_REFRESH, tkRefresh);

    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorization, "token"))
            return null;

        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }
}

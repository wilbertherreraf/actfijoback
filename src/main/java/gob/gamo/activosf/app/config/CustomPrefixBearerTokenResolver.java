package gob.gamo.activosf.app.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Change the prefix of the Authorization token from Bearer to Token.
 */
@Slf4j
@Component
public class CustomPrefixBearerTokenResolver implements BearerTokenResolver {
    private static final Pattern AUTHORIZATION_PATTERN =
            Pattern.compile("^Token (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    @Override
    public String resolve(HttpServletRequest request) {

        String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        String parameterToken =
                isParameterTokenSupportedForRequest(request) ? resolveFromRequestParameters(request) : null;

        if (authorizationHeaderToken != null) {
            log.info(
                    "XXX: en val support {} reqparm: {}, token {}",
                    isParameterTokenSupportedForRequest(request),
                    resolveFromRequestParameters(request),
                    parameterToken);
            if (parameterToken != null) {
                BearerTokenError error =
                        BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
            // return null;
        }
        log.info("XXX: saliendo de resolve nulo ");
        return null;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("en resolver header authorization: {} uri: {}", authorization, request.getRequestURL());

        if (!StringUtils.startsWithIgnoreCase(authorization, "token")) return null;

        Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            log.info("aqui {}", error.toString());
            throw new OAuth2AuthenticationException(error);
        }
        log.info("aqui 22222 {}", matcher.group("token"));
        return matcher.group("token");
    }

    private boolean isParameterTokenSupportedForRequest(HttpServletRequest request) {
        return ((("POST".equals(request.getMethod()))
                        && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType())) //
                || "GET".equals(request.getMethod()));
    }

    private String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        if (values == null || values.length == 0) return null;
        if (values.length == 1) return values[0];
        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }
}

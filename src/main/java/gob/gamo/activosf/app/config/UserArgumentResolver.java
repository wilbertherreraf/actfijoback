package gob.gamo.activosf.app.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.security.mapper.RoleMapper;

@Slf4j
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    // private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return User.anonymous();
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String userId = jwtAuthenticationToken.getName().strip();
        String token = jwtAuthenticationToken.getToken().getTokenValue().strip();

        User user = userRepository
                .findByUsername(userId)
                .map(it -> it.possessToken(token))
                .orElseThrow(() -> new InvalidBearerTokenException("`%s` is invalid token".formatted(token)));

        log.info(
                "in resolveArgument req auth:{} id: [{}] TK: {}",
                user.getUsername(),
                authentication.isAuthenticated(),
                token);
        return user;
    }

    // @Override
    public Object resolveArgument0(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        log.info("in resolveArgument req auth:{} ", authentication.isAuthenticated());
        if (authentication instanceof AnonymousAuthenticationToken) {
            return User.anonymous();
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String userId = jwtAuthenticationToken.getName().strip();
        String token = jwtAuthenticationToken.getToken().getTokenValue().strip();

        Instant exp = jwtAuthenticationToken.getToken().getExpiresAt();

        OAuth2TokenValidatorResult valid = JwtValidators.createDefault().validate(jwtAuthenticationToken.getToken());

        Date expire = Date.from(exp);

        log.info(
                "resolveArgument req userid:{} token: {} hasErrors?: {}",
                userId,
                token,
                isTokenExpired(expire),
                valid.hasErrors());

        jwtAuthenticationToken.getTokenAttributes().entrySet().forEach(entry -> {
            log.info("Detail Claims {} -> {}", entry.getKey(), entry.getValue());
        });

        log.info(
                "resolveArgument Eroors req userid:{} expired {}, hasErrors?: {} {}",
                userId,
                isTokenExpired(expire),
                valid.hasErrors(),
                valid.hasErrors());

        if (valid.hasErrors()) {
            for (OAuth2Error e : valid.getErrors()) {
                log.info("Error {} {}", e.getErrorCode(), e.getDescription());
                throw new InvalidBearerTokenException("Token invalido " + e.getErrorCode() + " " + e.getDescription());
            }
        }

        User user = userRepository
                .findById(Integer.valueOf(userId))
                .map(it -> it.possessToken(token))
                .orElseThrow(() -> new InvalidBearerTokenException("`%s` is invalid token".formatted(token)));
        log.info("saliendo de resolver ...");
        user.getRoles().forEach(r -> {
            log.info("rol {} {}", r.getCodrol(), r.getCodrecursos());
        });

        if (user != null) {
            Collection<? extends GrantedAuthority> grantedAuthorities = getAuthorities(user);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authentication, authentication.getCredentials(), grantedAuthorities);

            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails((HttpServletRequest) webRequest.getNativeRequest()));
            log.info("Authenticated user " + userId + ", setting security context!");
            securityContext.setAuthentication(authToken);
        }
        return user;
    }

    public Boolean isTokenExpired(Date exp) {
        return exp.before(new Date());
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.addAll(RoleMapper.toRoleDtos(user.getRoles()).stream()
                .map(p -> {
                    List<SimpleGrantedAuthority> grantedAuthorities1 = new ArrayList<>();
                    for (Recurso permiso : p.permisos()) {
                        log.info("privileg {}", p.codrol() + "." + permiso.getCodrec());
                        grantedAuthorities1.add(new SimpleGrantedAuthority(p.codrol() + "." + permiso.getCodrec()));
                    }
                    return grantedAuthorities1;
                })
                .toList()
                .stream()
                .flatMap(List::stream)
                .toList());

        return grantedAuthorities;
    }
}

package gob.gamo.activosf.app.config;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.security.SessionsSearcherService;
import gob.gamo.activosf.app.security.mapper.RoleMapper;
import gob.gamo.activosf.app.utils.UtilsDate;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private final JwtEncoder jwtEncoder;
    private final NimbusJwtDecoder jwtDecoder;
    private final AppProperties appProperties;
    private final SessionsSearcherService sessionsSearcherService;

    // private final JwtParser jwtParser =
    // Jwts.parserBuilder().setSigningKey(jwtEncoder).build();

    public String supply(User user) {

        Collection<? extends GrantedAuthority> grantedAuthorities = authoritiesUser(user);
        String tk = generateToken(user.getUsername(), grantedAuthorities);

        return tk;
    }

    /*
     * public Jws<Claims> validate(final String jwt) {
     * return jwtParser.parseClaimsJws(jwt);
     * }
     */
    public String generateToken(final String userId) {
        return generateToken(userId, new ArrayList<>());
    }

    public String generateToken(final String userId, Collection<? extends GrantedAuthority> grantedAuthorities) {
        String idSession = UUID.randomUUID().toString().replace("-", "");
        UserVO userVO = new UserVO(userId, "", idSession, "", "", new ArrayList<>());

        Gson gson = new Gson();
        sessionsSearcherService.createSession(idSession, gson.toJson(userVO));

        String authoritiesCad = grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                // .issuer("https://realworld.io")
                .issuedAt(now)
                .claim(AUTHORITIES_KEY, authoritiesCad)
                .claim(Constants.SEC_HEADER_TOKEN_REFRESH, idSession)
                .expiresAt(expirationDate().toInstant())
                .subject(userId)
                .build();
        Date exp = expirationDate();
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);
        String token = jwtEncoder.encode(parameters).getTokenValue();
        log.info("Generated bearer token with user id `{}`: expire: [{}] ", userId,
                UtilsDate.stringFromDate(exp, "H:mm:ss:SSS"));
        return token;
    }

    public Authentication getAuthenticationJwt(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        Jwt tokenJwt = jwtDecoder.decode(token);

        tokenJwt.getClaims().entrySet().forEach(t -> {
            log.info("::Claims entry {} -> {}", t.getKey(), t.getValue());
        });

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                tokenJwt.getClaim(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        JwtAuthenticationToken authToken = new JwtAuthenticationToken(tokenJwt, authorities, tokenJwt.getSubject());
        return authToken;
    }

    public String getRefreshToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        try {
            JWT parse = JWTParser.parse(token);
            if (parse.getJWTClaimsSet() != null && parse.getJWTClaimsSet().getClaims() != null) {
                String refTk = parse.getJWTClaimsSet().getClaims().getOrDefault(Constants.SEC_HEADER_TOKEN_REFRESH, "")
                        .toString();
                // boolean existTk = sessionsSearcherService.existsSession(refTk);
                return refTk;
            }
            /*
             * parse.getJWTClaimsSet().getClaims().get(Constants.SEC_HEADER_TOKEN_REFRESH);
             * for (Entry<String, Object> entry :
             * parse.getJWTClaimsSet().getClaims().entrySet()) {
             * if (entry.getKey().equalsIgnoreCase(Constants.SEC_HEADER_TOKEN_REFRESH)) {
             * return entry.getValue().toString();
             * }
             * }
             */
        } catch (ParseException e) {
            throw new UnsupportedJwtException(e.getMessage());
        }
        return null;
    }

    public String getClaim(String token, String idClaim) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        try {
            JWT parse = JWTParser.parse(token);
            if (parse.getJWTClaimsSet() != null && parse.getJWTClaimsSet().getClaims() != null) {
                String refTk = parse.getJWTClaimsSet().getClaims().getOrDefault(idClaim, "").toString();
                // boolean existTk = sessionsSearcherService.existsSession(refTk);
                return refTk;
            }
            /*
             * parse.getJWTClaimsSet().getClaims().get(Constants.SEC_HEADER_TOKEN_REFRESH);
             * for (Entry<String, Object> entry :
             * parse.getJWTClaimsSet().getClaims().entrySet()) {
             * if (entry.getKey().equalsIgnoreCase(Constants.SEC_HEADER_TOKEN_REFRESH)) {
             * return entry.getValue().toString();
             * }
             * }
             */
        } catch (ParseException e) {
            throw new UnsupportedJwtException(e.getMessage());
        }
        return null;
    }

    private Date expirationDate() {
        // UtilsDate.addTime(new Date(), 0, 0, false);
        final var expirationDate = System.currentTimeMillis()
                + (appProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds() * 1000L);
        return new Date(expirationDate);
    }

    private long getSessionTime0() {
        return appProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
    }

    public String getToken(final String userId) {
        return generateToken(userId);
    }

    public Optional<String> getSubFromToken(String token) {
        try {
            Jwt tokenJwt = jwtDecoder.decode(token);
            String sub = tokenJwt.getSubject().strip();
            return Optional.ofNullable(sub);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateToken(String authToken) {
        try {
/*             try {
                Date e = JWTParser.parse(authToken).getJWTClaimsSet().getExpirationTime();
                log.info("expiraaaaaaaaaaaaa exp: {} now: {}", UtilsDate.stringFromDate(e, "H:mm:ss:SSS"),
                        UtilsDate.stringFromDate(new Date(), "H:mm:ss:SSS"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } */

            Jwt tokenJwt = jwtDecoder.decode(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Valid Invalid JWT signature." + e.getMessage());
        } catch (ExpiredJwtException | JwtValidationException e) {
            log.error("Valid Expired JWT token." + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Valid Unsupported JWT token." + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Valid JWT token compact of handler are invalid." + e.getMessage());
        }
        return false;
    }

    public Collection<? extends GrantedAuthority> authoritiesUser(User user) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.addAll(RoleMapper.toRoleDtos(user.getRoles()).stream()
                .map(p -> {
                    List<SimpleGrantedAuthority> grantedAuthorities1 = new ArrayList<>();
                    grantedAuthorities1.add(new SimpleGrantedAuthority(p.codrol().toUpperCase()));
                    for (Recurso permiso : p.permisos()) {
                        // log.info("privileg {}", p.codrol() + "." + permiso.getCodrec());
                        grantedAuthorities1.add(new SimpleGrantedAuthority((p.codrol() + "." + permiso.getCodrec()).toUpperCase()));
                        SimpleGrantedAuthority grantP = new SimpleGrantedAuthority((permiso.getCodrec()).toUpperCase());
                        if (!grantedAuthorities1.contains(grantP)) {
                            grantedAuthorities1.add(grantP);
                        }
                    }
                    return grantedAuthorities1;
                })
                .toList()
                .stream()
                .flatMap(List::stream)
                .toList());
        log.info("XXX: permissions added {}", grantedAuthorities.size());
        return grantedAuthorities;
    }
}

package gob.gamo.activosf.app.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    /*
     * @Autowired
     * private UserRepository userRepository;
     *
     * @Value("${security.key.public}")
     * RSAPublicKey rsaPublicKey;
     */
    private final AuthEntryPointJwt authEntryPointJwt;
    private List<HttpStatus> errorCodes;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ExceptionHandleFilter exceptionHandleFilter)
            throws Exception {
        return http.cors(withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // .cors( SecurityConfigurerAdapter::and)
                .authorizeHttpRequests(requests -> requests.requestMatchers(
                                HttpMethod.POST,
                                Constants.API_ROOT_VERSION + Constants.API_LOGIN,
                                Constants.API_ROOT_VERSION + Constants.API_PUBLIC + "/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .oauth2ResourceServer(s -> s.jwt(withDefaults()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .exceptionHandling(handler -> {
                    log.info("Error Sec... {}", handler.getClass());
                    // handler.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    handler.authenticationEntryPoint(authEntryPointJwt)
                            .accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .addFilterBefore(exceptionHandleFilter, UsernamePasswordAuthenticationFilter.class)
                // .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
                .addFilterAfter(new ClientErrorLoggingFilter(errorCodes), AuthorizationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        // cors.setAllowedOrigins(Arrays.asList("*"));
        cors.setAllowedOriginPatterns(List.of("*"));
        cors.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()));
        cors.setAllowedHeaders(List.of("Authorization", "X-Activosf", "X-PINGOTHER", "Content-Type")); // orginal "*"
        // cors.setAllowedHeaders(List.of("*")); // orginal "*" "X-PINGOTHER",
        // "Content-Type"
        cors.setExposedHeaders(List.of("X-Activosf", "Content-Type", "Link", "X-Total-Count"));
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${security.key.public}") RSAPublicKey rsaPublicKey) {
        log.info("XXX: en decoder!...{}", rsaPublicKey);

        NimbusJwtDecoder n = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
        n.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                Arrays.asList(new JwtTimestampValidator(Duration.of(-1, ChronoUnit.SECONDS)))));
        return n; // NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(
            @Value("${security.key.public}") RSAPublicKey rsaPublicKey,
            @Value("${security.key.private}") RSAPrivateKey rsaPrivateKey) {
        log.info("XXX: en encoder...{}", rsaPublicKey);
        JWK jwk = new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    // @Bean
    public StrictHttpFirewall httpFirewall() {

        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        return firewall;
    }

    // @Bean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }
}

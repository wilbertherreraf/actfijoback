package gob.gamo.activosf.app.security;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.repository.sec.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Slf4j
// @Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.info("Authenticating {}", login);

        /*         if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmail(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User " + lowercaseLogin + " was not found in the database")); */
        return null;
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(
            String lowercaseLogin, User user) {
        /*
         * if (!user.getActivated()) {
         * throw new UserNotActivatedException("User " + lowercaseLogin +
         * " was not activated");
         * }
         */
        /* List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getCodrol()))
        .collect(Collectors.toList()); */

        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        /*   grantedAuthorities.addAll(RoleMapper.toRoleDtos(user.getRoles()).stream()
        .map(roleDto -> {
            List<SimpleGrantedAuthority> grantedAuthorities1 = new ArrayList<>();
            return roleDto.permisosList().stream().map(pageDtoListEntry -> {

                grantedAuthorities1.add(new SimpleGrantedAuthority(
                        roleDto.codrol() + "." + pageDtoListEntry));

                return grantedAuthorities1;
            }).toList().stream().flatMap(List::stream).toList();

            // simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" +
            // roleDto.getName()));
        }).toList().stream().flatMap(List::stream).toList()); */
        log.info("en getautho");
        grantedAuthorities.forEach(autho -> {
            log.info("grante {}", autho.getAuthority());
        });
        return grantedAuthorities;
    }
}

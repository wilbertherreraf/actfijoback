package gob.gamo.activosf.app.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.entities.User;

@Slf4j
@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        /*
         * if (user.isSpecialPrivileges()) {
         * grantedAuthorities.addAll(
         * extractSpecialPrivileges().stream().map(
         * pagesPrivileges -> new SimpleGrantedAuthority(
         * pagesPrivileges.getPage().getName() + "." +
         * pagesPrivileges.getPrivilege().getName()
         * )).toList()
         * );
         * }
         */

        /*         grantedAuthorities.addAll(RoleMapper.toRoleDtos(user.getRoles()).stream()
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
        log.info("en getautho111111111");
        grantedAuthorities.forEach(autho -> {
            log.info("grante {}", autho.getAuthority());
        });
        return grantedAuthorities;
    }

    /*
     * private Collection<PagesPrivileges> extractSpecialPrivileges() {
     * if (user.isSpecialPrivileges()) {
     * return user.getRolePagesPrivileges().stream().map(RolePagesPrivileges::
     * getPagesPrivileges).toList();
     * }
     * return Collections.emptyList();
     * }
     */

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // user.isEnabled();
    }
}

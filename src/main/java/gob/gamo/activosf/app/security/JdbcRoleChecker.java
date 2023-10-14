package gob.gamo.activosf.app.security;

import java.util.Collection;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import io.micrometer.common.util.StringUtils;

@Slf4j
@Component(value = "roleChecker")
public class JdbcRoleChecker implements RoleChecker {
    private HttpServletRequest request;

    public JdbcRoleChecker(HttpServletRequest request) {
        log.info("creando role checker...");
        this.request = request;
    }
 
    @Override
    public boolean check(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Unauthorized user or invalid url access!");
            return false;
        }

        String pageCode = this.request.getParameter("pageCode") != null
                ? this.request.getParameter("pageCode").toUpperCase()
                : "";
        if (StringUtils.isBlank(pageCode) && authentication.isAuthenticated()) {
            log.warn("Acceso autorizado por token valido pero sin parametro 'pageCode'");
            return true;
        }
        log.info(
                "en checker {}({}) [{}]",
                pageCode,
                request.getRequestURL().toString(),
                authentication.getAuthorities().size());
        // has access to requested page
        if (hasAccessToPage(authentication.getAuthorities(), pageCode, request.getMethod())) {
            // has authorization to requested action on requested page
            log.warn("User {} performed {} on {} ", authentication.getName(), request.getMethod(), pageCode);
            return true;
        } else {
            log.warn("User with id {} tried to access unauthorized page {}", authentication.getName(), pageCode);
        }

        return false;
    }

    boolean hasAccessToPage(Collection<? extends GrantedAuthority> authorities, String pageCode, String method) {
        String privilege =
                switch (method.toUpperCase()) {
                    case "GET" -> PRIVILEGE.READ;
                    case "PUT" -> PRIVILEGE.WRITE;
                    case "POST" -> PRIVILEGE.WRITE;
                    case "DELETE" -> PRIVILEGE.DELETE;
                    default -> "";
                };
        String codeAth = "";
        boolean hasAccess = false;
        // users.write
        for (GrantedAuthority authority : authorities) {
            boolean contentPrivilege = authority.getAuthority().endsWith("." + privilege);
            if (authority.getAuthority().split("\\.")[0].equals(pageCode)
                    || authority.getAuthority().equals(pageCode) //
                    || (contentPrivilege && (pageCode + "." + privilege).equals(authority.getAuthority()))) {
                hasAccess = true;
                codeAth = authority.getAuthority();
                break;
            }
        }
        log.info(
                "XXX: param page({}) url: {} with access[{}] founded?: {} -> {}",
                authorities.size(),
                request.getRequestURL().toString(),
                pageCode,
                hasAccess,
                codeAth);
        return hasAccess;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // todo: implement
    }

    private boolean hasAuthorityToActOnPage(
            Collection<? extends GrantedAuthority> authorities, String pageCode, String method) {
        String privilege =
                switch (method.toUpperCase()) {
                    case "GET" -> PRIVILEGE.READ;
                    case "PUT" -> PRIVILEGE.UPDATE;
                    case "POST" -> PRIVILEGE.WRITE;
                    case "DELETE" -> PRIVILEGE.DELETE;
                    default -> null;
                };

        for (GrantedAuthority authority : authorities) {
            log.info(
                    "E na has prov [{}] {} -> {}",
                    method.toUpperCase(),
                    (pageCode + "." + privilege),
                    authority.getAuthority());
            if ((pageCode + "." + privilege).equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}

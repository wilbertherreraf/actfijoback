package gob.gamo.activosf.app.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;

public interface RoleChecker extends InitializingBean {
    boolean check(Authentication authentication);
}

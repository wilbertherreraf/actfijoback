package gob.gamo.activosf.app.dto.sec;

import gob.gamo.activosf.app.domain.entities.User;

public record UserVO(String username, String email, String token, String codempleado, String nombres) {
    public UserVO(User user) {
        this(user.getUsername(), user.getEmail(), user.getToken(), user.getCodEmpleado(), user.getNombres());
    }
}

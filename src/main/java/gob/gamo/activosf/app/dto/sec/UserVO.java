package gob.gamo.activosf.app.dto.sec;

import java.util.List;

import gob.gamo.activosf.app.domain.entities.User;

public record UserVO(
        String username, String email, String token, String codpersona, String nombres, List<RolesVO> roles) {
    public UserVO(User user) {
        this(
                user.getUsername(),
                user.getEmail(),
                user.getToken(),
                user.getCodPersona(),
                user.getNombres(),
                user.getRoles().stream().map(r -> new RolesVO(r)).toList());
    }
}

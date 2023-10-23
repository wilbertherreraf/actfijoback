package gob.gamo.activosf.app.dto;

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.UserVO;

public record UserPersonaVo(
        Integer id, Integer idPersona, PersonaVO persona, Integer idEmpleado, EmpleadoVo empleado, UserVO user) {
    public UserPersonaVo(User user) {
        this(new UserVO(user), null, null);
    }

    public UserPersonaVo(User user, PersonaVO persona) {
        this(new UserVO(user), persona, null);
    }
    ;

    public UserPersonaVo(UserVO user, PersonaVO persona, EmpleadoVo empleado) {
        this(
                user != null ? user.id() : null,
                persona != null ? persona.idPersona() : null,
                persona,
                empleado != null ? empleado.id() : null,
                empleado,
                user);
    }
}

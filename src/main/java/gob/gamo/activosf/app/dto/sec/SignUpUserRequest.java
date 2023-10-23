package gob.gamo.activosf.app.dto.sec;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public record SignUpUserRequest(
        Integer id,
        String email,
        String username,
        String password,
        String nombres,
        String codpersona,
        Integer idPersona) {}

package gob.gamo.activosf.app.dto.sec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public record UpdateUserRequest(String email, String username, String password, String nombres, String codpersona,Integer idPersona, List<RolesVO> roles) {
    public UpdateUserRequest {
        if (email == null)
            email = "";
        if (username == null)
            username = "";
        if (password == null)
            password = "";
        if (codpersona == null)
            codpersona = "";
        if (nombres == null)
            nombres = "";
        if (roles == null)
            roles = new ArrayList<>();
    }
}

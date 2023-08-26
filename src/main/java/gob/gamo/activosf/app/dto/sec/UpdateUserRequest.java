package gob.gamo.activosf.app.dto.sec;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public record UpdateUserRequest(String email, String username, String password, String nombres, String image) {
    public UpdateUserRequest {
        if (email == null) email = "";
        if (username == null) username = "";
        if (password == null) password = "";
        if (nombres == null) nombres = "";
        if (image == null) image = "";
    }
}

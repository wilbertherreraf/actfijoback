package gob.gamo.activosf.app.dto.sec;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public record LoginUserRequest(String username, String password) {}

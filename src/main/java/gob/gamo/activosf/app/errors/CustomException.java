package gob.gamo.activosf.app.errors;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CustomException {
    private String name;
    private String message;
    private Object description;
    private LocalDateTime timestamp;

    public CustomException(String name, String message, Object description, LocalDateTime timestamp) {
        this.name = name;
        this.message = message;
        this.description = description;
        this.timestamp = timestamp;
    }
}

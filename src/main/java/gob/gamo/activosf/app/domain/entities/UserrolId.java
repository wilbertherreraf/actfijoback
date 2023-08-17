package gob.gamo.activosf.app.domain.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserrolId implements Serializable {
    private Integer userId;
    private Integer rolId;
}
    


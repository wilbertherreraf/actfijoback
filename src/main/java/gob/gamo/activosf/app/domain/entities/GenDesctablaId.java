package gob.gamo.activosf.app.domain.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenDesctablaId implements Serializable {
    @Column(name = "des_codtab")
    private Integer desCodtab;

    @Column(name = "des_codigo")
    private Integer desCodigo;


}
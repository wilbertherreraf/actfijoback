package gob.gamo.activosf.app.domain.entities;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "gen_desctabla")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenDesctabla {

    @EmbeddedId
    private GenDesctablaId id;

    @Column(name = "des_codeiso")
    private String desCodeiso;

    @Column(name = "des_descrip")
    private String desDescrip;

    @Column(name = "des_codrec")
    private String desCodrec;
    @Column(name = "des_codigopadre")    
    private Integer desCodigopadre;
}

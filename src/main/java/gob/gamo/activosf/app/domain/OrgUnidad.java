package gob.gamo.activosf.app.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "org_unidad")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrgUnidad {

    @Id
    @Column(name = "id_unidad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id_unidad;
    
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "sigla")
    private String sigla;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "tipo_unidad")
    private String tipo_unidad;
    @Column(name = "id_unidad_padre")
    private Integer id_unidad_padre;
    @Column(name = "estado")
    private String estado;
}

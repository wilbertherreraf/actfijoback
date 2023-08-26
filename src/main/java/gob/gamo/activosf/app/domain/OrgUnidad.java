package gob.gamo.activosf.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import gob.gamo.activosf.app.dto.UnidadResponse;

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
    private Integer idUnidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "tipo_unidad")
    private String tipoUnidad;

    @Column(name = "id_unidad_padre")
    private Integer idUnidadPadre;

    @Column(name = "estado")
    private String estado;

    public static OrgUnidad createOrgUnidad(UnidadResponse req) {
        return OrgUnidad.builder()
                .idUnidad(req.id())
                .domicilio(req.domicilio())
                .idUnidadPadre(req.idUnidadPadre())
                .nombre(req.nombre())
                .sigla(req.sigla())
                .telefono(req.telefono())
                .tipoUnidad(req.tipoUnidad())
                .build();
    }
}

package gob.gamo.activosf.app.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.dto.UnidadResponse;

@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "idUnd")
@Entity
@Setter
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

    @Column(name = "tabrolempleado")
    private Integer tabRolempleado;

    @Column(name = "rolempleado")
    private Integer rolempleado;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
                updatable = false,
                insertable = false,
                name = "tabrolempleado",
                referencedColumnName = "des_codtab"),
        @JoinColumn(updatable = false, insertable = false, name = "rolempleado", referencedColumnName = "des_codigo")
    })
    private GenDesctabla rolempleadodesc;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            updatable = false,
            insertable = false,
            name = "id_unidad_padre",
            referencedColumnName = "id_unidad",
            nullable = true)
    private OrgUnidad unidadPadre;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidadPadre")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<OrgUnidad> children = new HashSet<>();

    @Column(name = "estado")
    private String estado;

    // @JsonIgnore
    /*    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "org_unidad_emp", //
            joinColumns = @JoinColumn(name = "id_unidad") //
            , inverseJoinColumns = @JoinColumn(name = "id_empleado") //
    )
    private Set<OrgEmpleado> empleados0 = new HashSet<>(); */

    @Transient
    private OrgEmpleado empleadoBoss;

    public static OrgUnidad createOrgUnidad(UnidadResponse req) {
        return OrgUnidad.builder()
                .idUnidad(req.id())
                .domicilio(req.domicilio())
                .idUnidadPadre(req.idUnidadPadre())
                .tabRolempleado(req.tabRolempleado())
                .rolempleado(req.rolempleado())
                .nombre(req.nombre())
                .sigla(req.sigla())
                .telefono(req.telefono())
                .tipoUnidad(req.tipoUnidad())
                .build();
    }
}

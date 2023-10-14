package gob.gamo.activosf.app.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "id")
@Entity
@Setter
@Getter
@Builder
@Table(name = "org_empleado")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgEmpleado {

    @Id
    @Column(name = "id_empleado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_unidad")
    private Integer idUnidad;

    @Column(name = "cod_internoempl")
    private String codInternoempl;

    @Column(name = "id_persona")
    private Integer idPersona;

    @Column(name = "cod_persona")
    private String codPersona;

    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "tabrolempleado")
    private Integer tabRolempleado;

    @Column(name = "rolempleado")
    private Integer rolempleado;

    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)    
    private Date fechaIngreso;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Column(name = "estado")
    private String estado;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(updatable = false, insertable = false, name = "tabrolempleado", referencedColumnName = "des_codtab"),
        @JoinColumn(updatable = false, insertable = false, name = "rolempleado", referencedColumnName = "des_codigo")
    })
    private GenDesctabla rolempleadodesc;   
     
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, insertable = false, name = "id_persona")
    private OrgPersona persona;   

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, insertable = false, name = "id_unidad")
    private OrgUnidad unidad;     

    /* @Builder.Default
    @ManyToMany(mappedBy = "empleados", fetch = FetchType.LAZY)
    private Set<OrgUnidad> unidades = new HashSet<>(); */

    // @OneToOne
    /* @JoinColumn(name = "id_persona")
    private OrgPersona persona; */
}

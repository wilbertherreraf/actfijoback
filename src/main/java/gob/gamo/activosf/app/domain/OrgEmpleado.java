package gob.gamo.activosf.app.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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

    @Column(name = "cod_internoempl")
    private String cod_internoempl;

    @Column(name = "id_persona")
    private String id_persona;

    @Column(name = "id_cargo")
    private String id_cargo;

    @Column(name = "fecha_ingreso")
    private String fecha_ingreso;

    @Column(name = "fecha_baja")
    private String fecha_baja;

    @Column(name = "estado")
    private String estado;

    @JsonIgnore
    @Builder.Default
    @ManyToMany(mappedBy = "empleados", fetch = FetchType.LAZY)
    private Set<OrgUnidad> unidades = new HashSet<>();
}

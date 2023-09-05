package gob.gamo.activosf.app.domain;

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
@Table(name = "org_unidad_emp")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgUnidadEmp {
    @Id
    @Column(name = "id_unid_empl")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnidEmpl;

    @Column(name = "id_unidad")
    private String idUnidad;

    @Column(name = "id_empleado")
    private String idEmpleado;

    @Column(name = "id_emplresponsable")
    private String idEmplresponsable;

    @Column(name = "fecha_reg")
    private String fechaReg;

    @Column(name = "estado")
    private String estado;
}

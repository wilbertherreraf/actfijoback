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
    private String id_unidad;

    @Column(name = "id_emplresponsable")
    private String id_emplresponsable;

    @Column(name = "id_emplsubordinado")
    private String id_emplsubordinado;

    @Column(name = "fecha_reg")
    private String fecha_reg;

    @Column(name = "estado")
    private String estado;
}

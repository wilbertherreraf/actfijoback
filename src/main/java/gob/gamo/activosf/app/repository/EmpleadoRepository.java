package gob.gamo.activosf.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.OrgEmpleado;

public interface EmpleadoRepository extends JpaRepository<OrgEmpleado, Integer> {
    @Query("Select e from OrgEmpleado e, OrgPersona p where e.idPersona = p.idPersona "
            + " and p.idPersona = :idPersona ")
    List<OrgEmpleado> findAllByIdPersona(@Param(value = "idPersona") Integer idPersona);

    @Query("Select e from OrgEmpleado e, OrgPersona p " + "where e.idPersona = p.idPersona "
            + "and p.idPersona = :idPersona "
            + "and e.idUnidad > 0 "
            + "and e.rolempleado is not null "
            + "and e.fechaBaja is null ")
    List<OrgEmpleado> findByIdPersonaActivo(@Param(value = "idPersona") Integer idPersona);

    @Query(
            "Select e from OrgEmpleado e, OrgPersona p where e.idPersona = p.idPersona "
                    + "and p.idPersona = :idPersona " + "and e.fechaBaja is null "
                    + "and e.fechaIngreso = (select max(e2.fechaIngreso) from OrgEmpleado e2 where e2.idPersona = e.idPersona and e2.fechaIngreso <= :fecha) ")
    Optional<OrgEmpleado> findByIdPersonaActivo(
            @Param(value = "idPersona") Integer idPersona, @Param(value = "fecha") Date fecha);

    @Query("Select e from OrgEmpleado e, OrgPersona p where e.idPersona = p.idPersona "
            + "and p.idPersona = :idPersona " + "and e.idCargo = :idCargo "
            + "and e.fechaIngreso = (select max(e2.fechaIngreso) from OrgEmpleado e2 "
            + "where e2.idPersona = e.idPersona and e2.fechaIngreso <= :fecha) ")
    List<OrgEmpleado> findByAllIdPersonaCargo(
            @Param(value = "idPersona") Integer idPersona,
            @Param(value = "idCargo") Integer idCargo,
            @Param(value = "fecha") Date fecha);

    @Query("Select e from OrgEmpleado e, OrgUnidad p where e.idUnidad = p.idUnidad " + " and p.idUnidad = :id  ")
    List<OrgEmpleado> empleadosUnidad(@Param(value = "id") Integer idUnidad);

    @Query("Select e from OrgEmpleado e, OrgUnidad p where e.idUnidad = p.idUnidad "
            + " and p.idUnidad = :id and e.fechaBaja is null ")
    List<OrgEmpleado> empleadosUnidadAct(@Param(value = "id") Integer idUnidad);

    @Query("Select e from OrgEmpleado e, OrgUnidad p where e.idUnidad = p.idUnidad "
            + " and p.idUnidad = :id and e.rolempleado = :rolempleado and e.fechaBaja is null ")
    List<OrgEmpleado> empleadosUnidad(
            @Param(value = "id") Integer idUnidad, @Param(value = "rolempleado") Integer rolempleado);

    @Query("Select e from OrgEmpleado e, OrgUnidad p " + " where e.idUnidad = p.idUnidad "
            + " and p.idUnidad = :id and e.fechaBaja is null "
            + " and e.rolempleado = p.rolempleado ")
    List<OrgEmpleado> empleadosBoss(@Param(value = "id") Integer idUnidad);

    @Query("Select e from OrgEmpleado e, OrgUnidad p " + " where e.idUnidad = p.idUnidad "
            + " and p.idUnidad = :id and e.fechaBaja is null "
            + " and p.idEmpleado = e.id "
            + " and e.rolempleado = p.rolempleado ")
    Optional<OrgEmpleado> empleadoBoss(@Param(value = "id") Integer idUnidad);

    @Query("Select e from OrgEmpleado e, OrgUnidad p " + "where e.idUnidad = p.idUnidad "
            + "and e.fechaBaja is null "
            + "and e.rolempleado = p.rolempleado "
            + "and e.fechaIngreso = (select max(e2.fechaIngreso) from OrgEmpleado e2 "
            + "where e2.idUnidad = e.idUnidad "
            + "and e2.idPersona = e.idPersona) ")
    List<OrgEmpleado> empleadosBoss();
}

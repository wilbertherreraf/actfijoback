package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.OrgEmpleado;

public interface EmpleadoRepository extends JpaRepository<OrgEmpleado, Integer> {
    // findOrgEmpleados
    // List<OrgEmpleado> findEmpleadosByUnidadesId(Integer id);
}

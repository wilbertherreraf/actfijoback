package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;

public interface PersonaRepository extends JpaRepository<OrgPersona, Integer> {
    
}

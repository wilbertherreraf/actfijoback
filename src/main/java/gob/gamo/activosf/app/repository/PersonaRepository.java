package gob.gamo.activosf.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.OrgPersona;

public interface PersonaRepository extends JpaRepository<OrgPersona, Integer>, JpaSpecificationExecutor<OrgPersona> {
    Optional<OrgPersona> findByNumeroDocumento(String numeroDocumento);
}

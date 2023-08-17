package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfCodigoContable;

/**
 *
 * @author wherrera
 */

public interface AfCodigoContableRepository extends JpaRepository<AfCodigoContable, Integer> {
    @Query("SELECT a FROM AfCodigoContable a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.codigo")
    public List<AfCodigoContable> findAllActivesByGestion(Integer gestion);
}

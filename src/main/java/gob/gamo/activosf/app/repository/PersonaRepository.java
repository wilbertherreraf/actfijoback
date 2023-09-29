package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.OrgPersona;

public interface PersonaRepository extends JpaRepository<OrgPersona, Integer>, JpaSpecificationExecutor<OrgPersona> {
    /* @Query("SELECT * FROM OrgPersona WHERE nombre LIKE CONCAT('%', :nombre, '%')"
//    + " ORDER BY PUBLICATION_DATE"
    + " OFFSET :start"
    + " FETCH NEXT :rowCount ROWS ONLY")
    List<OrgPersona> findByNombreContains(@Param(value = "nombre") String title,@Param(value = "start") int start,@Param(value = "rowCount") int rowCount); */
}

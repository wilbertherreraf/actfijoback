package gob.gamo.activosf.app.repository;

import java.util.Optional;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.OrgUnidad;

public interface OrgUnidadRepository extends JpaRepository<OrgUnidad, Integer> {
    Optional<OrgUnidad> findBySigla(String sigla);

    //Page<OrgUnidad> findAll(Pageable pageable);
}

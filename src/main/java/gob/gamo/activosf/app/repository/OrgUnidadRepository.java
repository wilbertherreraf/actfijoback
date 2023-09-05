package gob.gamo.activosf.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.OrgUnidad;

public interface OrgUnidadRepository extends JpaRepository<OrgUnidad, Integer> {
    Optional<OrgUnidad> findBySigla(String sigla);

    Page<OrgUnidad> findAll(Pageable pageable);

    Page<OrgUnidad> findAll(Specification<OrgUnidad> specification, Pageable pageable);
}

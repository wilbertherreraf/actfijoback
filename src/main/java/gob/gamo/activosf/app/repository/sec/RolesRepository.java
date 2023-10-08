package gob.gamo.activosf.app.repository.sec;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import gob.gamo.activosf.app.domain.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer>, JpaSpecificationExecutor<Roles>  {
    Optional<Roles> findByCodrol(String codrol);

    Page<Roles> findAll(Pageable pageable);
    Page<Roles> findAll(Specification<Roles> specification, Pageable pageable);
}

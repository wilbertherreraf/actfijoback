package gob.gamo.activosf.app.repository.sec;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByCodrol(String codrol);

    Page<Roles> findAll(Pageable pageable);
}

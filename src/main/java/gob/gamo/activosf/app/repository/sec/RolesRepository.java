package gob.gamo.activosf.app.repository.sec;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.Roles;

import java.util.List;
import java.util.Optional;


public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByCodrol(String codrol);
    Page<Roles> findAll(Pageable pageable);
}

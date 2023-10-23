package gob.gamo.activosf.app.repository.sec;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String login);

    boolean existsByUsername(String login);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByCodPersona(String codPersona);

    @Query("select b from User b where b.idUnidEmpl = :idPersona ")
    Optional<User> findByIdPersona(@Param(value = "idPersona") Integer idPersona);

    Page<User> findAll(Specification<User> specification, Pageable pageable);
}

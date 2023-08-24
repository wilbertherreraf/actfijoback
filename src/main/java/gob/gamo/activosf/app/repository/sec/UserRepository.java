package gob.gamo.activosf.app.repository.sec;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String login);

    boolean existsByUsername(String login);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}

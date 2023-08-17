package gob.gamo.activosf.app.repository.sec;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.Recurso;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Integer>{
    Optional<Recurso> findByCodrec(String codrec);
}
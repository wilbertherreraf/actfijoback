package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.GenTablas;

public interface GenTablasRepository extends JpaRepository<GenTablas, Integer> {
    
}

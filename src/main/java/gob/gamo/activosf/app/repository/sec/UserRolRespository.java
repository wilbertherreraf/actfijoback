package gob.gamo.activosf.app.repository.sec;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.Userrol;
import gob.gamo.activosf.app.domain.entities.UserrolId;

public interface UserRolRespository extends JpaRepository<Userrol, UserrolId> {}

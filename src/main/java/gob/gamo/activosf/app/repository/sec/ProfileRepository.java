package gob.gamo.activosf.app.repository.sec;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.ProfileId;
import gob.gamo.activosf.app.domain.entities.Recurso;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId>{
    List<Profile> findByIdRolId(Integer rolId);
}

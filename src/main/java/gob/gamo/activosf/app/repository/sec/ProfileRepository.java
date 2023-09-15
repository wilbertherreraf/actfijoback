package gob.gamo.activosf.app.repository.sec;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.ProfileId;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId>{
    @Query("select b from Profile b where b.id.rolId = :rolId and b.id.recursoId = :recursoId  ")
    Optional<Profile> findByRolIdRecursoId(@Param("rolId") Integer rolId, @Param("recursoId") Integer recursoId);

    @Query("select b from Profile b where b.id.rolId = :rolId ")
    List<Profile> findByIdRolId(@Param("rolId") Integer rolId);

    @Modifying
    @Query("delete from Profile b where b.id.rolId = :rolId and b.id.recursoId = :recursoId  ")
    void deleteProfile(@Param("rolId") Integer rolId, @Param("recursoId") Integer recursoId);
}

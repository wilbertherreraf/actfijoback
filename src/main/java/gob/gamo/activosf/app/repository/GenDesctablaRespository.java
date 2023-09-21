package gob.gamo.activosf.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.domain.entities.GenDesctablaId;

public interface GenDesctablaRespository extends JpaRepository<GenDesctabla, GenDesctablaId> {
    @Query("SELECT t FROM GenDesctabla t WHERE t.id.desCodtab = :desCodtab")
    public List<GenDesctabla> findByDesCodtab(@Param(value = "desCodtab") Integer desCodtab);

    @Query("SELECT t FROM GenDesctabla t WHERE t.id.desCodtab = :desCodtab and t.id.desCodigo = :desCodigo ")
    public Optional<GenDesctabla> findByDesCodtabAndDesCodigo(
            @Param(value = "desCodtab") Integer desCodtab, @Param(value = "desCodigo") Integer desCodigo);
}

package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.TxTransdet;

public interface TxTransdetRepository extends JpaRepository<TxTransdet, Integer> {
    @Query("Select e from TxTransdet e where idTransaccion = :idTransaccion")
    List<TxTransdet> findByIdTransaccion(@Param(value = "idTransaccion") Integer idTransaccion);
}

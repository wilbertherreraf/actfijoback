/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.TxTransaccion;

/**
 *
 * @author wherrera
 */
public interface TxTransaccionRepository extends JpaRepository<TxTransaccion, Integer> {
    @Query("Select e " + "from TxTransaccion e, AfItemaf a "
            + "where e.idItemaf = a.id "
            + "and a.id = :idItemaf "
            + "and e.tipoopersub = :tipoopersub "
            + "and e.tipooperacion = :tipooperacion "
            +
            // "and tareaoperacion = :tareaoperacion " +
            "order by e.fechaOper ")
    List<TxTransaccion> findByItemaf(
            @Param(value = "tipoopersub") Integer tipoopersub,
            @Param(value = "tipooperacion") Integer tipoOperacion,
            // @Param(value = "tareaoperacion") Integer tareaoperacion,
            @Param(value = "idItemaf") Integer idItemaf);

    @Query("Select e " + "from TxTransaccion e, AfItemaf a "
            + "where e.idTransaccion = a.idTrxkdx "
            + "and e.idItemaf = a.id "
            + "and a.id = :idItemaf "
            +
            // "and e.tipoopersub = :tipoopersub " +
            // "and e.tipooperacion = :tipooperacion " +
            // "and tareaoperacion = :tareaoperacion " +
            "order by e.fechaOper ")
    Optional<TxTransaccion> kardexVig(@Param(value = "idItemaf") Integer idItemaf);
}

package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.TxTransdet;

public interface TxTransdetRepository extends JpaRepository<TxTransdet, Integer> {
    @Query("Select e from TxTransdet e where idTransaccion = :idTransaccion")
    List<TxTransdet> findByIdTransaccion(@Param(value = "idTransaccion") Integer idTransaccion);

    @Query("Select e from TxTransdet e where idTransaccion = :idTransaccion")
    Page<TxTransdet> findByIdTransaccion(@Param(value = "idTransaccion") Integer idTransaccion, Pageable pageable);

    @Query("Select e " + "from TxTransdet e "
            + "where tipooperacion = :tipooperacion "
            + "and tareaoperacion = :tareaoperacion "
            + "and detoperacion = :detoperacion "
            + "and opermayor = :opermayor "
            + "and tipocargo = :tipoCargo "
            + "and idItemaf = :idItemaf "
            + "and monto = montoCont "
            +
            // "and montoCont != 0 " +
            "and cantidad > 0 "
            + "order by e.fechaValor, e.idTransdet ")
    List<TxTransdet> findByItemFifo(
            @Param(value = "tipooperacion") Integer tipoOperacion,
            @Param(value = "tareaoperacion") Integer tareaoperacion,
            @Param(value = "detoperacion") Integer detoperacion,
            @Param(value = "opermayor") Integer opermayor,
            @Param(value = "tipoCargo") Integer tipoCargo,
            @Param(value = "idItemaf") Integer idItemaf);

    @Query("Select e " + "from TxTransaccion t, TxTransdet e "
            + "where t.idTransaccion = e.idTransaccion "
            + "and t.idItemaf = e.idItemaf "
            + "and t.idItemaf = :idItemaf "
            + "and e.tipooperacion = :tipooperacion "
            + "and e.tareaoperacion = :tareaoperacion "
            + "and e.detoperacion = :detoperacion "
            + "and e.opermayor = :opermayor "
            + "and e.tipocargo = :tipoCargo "
            + "and e.montoCont != 0 "
            + "order by e.fechaValor, e.idTransdet ")
    List<TxTransdet> findByItemKardex(
            @Param(value = "tipooperacion") Integer tipoOperacion,
            @Param(value = "tareaoperacion") Integer tareaoperacion,
            @Param(value = "detoperacion") Integer detoperacion,
            @Param(value = "opermayor") Integer opermayor,
            @Param(value = "tipoCargo") Integer tipoCargo,
            @Param(value = "idItemaf") Integer idItemaf);

    @Query("Select e " + "from TxTransaccion t, TxTransdet e "
            + "where t.idTransaccion = e.idTransaccion "
            + "and t.idItemaf = e.idItemaf "
            + "and t.idTransaccion = :idTransaccion "
            + "and t.idItemaf = :idItemaf "
            + "and e.tipooperacion = :tipooperacion "
            + "and e.detoperacion = :detoperacion "
            + "and e.opermayor = :opermayor "
            +
            // "and e.tipocargo = :tipoCargo " +
            // "and e.montoCont != 0 " +
            "order by e.fechaValor, e.idTransdet ")
    List<TxTransdet> findByTrxItemKardex(
            @Param(value = "idTransaccion") Integer idTransaccion,
            @Param(value = "tipooperacion") Integer tipoOperacion,
            @Param(value = "detoperacion") Integer detoperacion,
            @Param(value = "opermayor") Integer opermayor,
            // @Param(value = "tipoCargo") Integer tipoCargo,
            @Param(value = "idItemaf") Integer idItemaf);
}

package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.TxArea;

public interface TxAreaRepository extends JpaRepository<TxArea, Integer>{

    @Query("SELECT a FROM TxArea a WHERE a.estado = 'A' ORDER BY a.nombre")
	public List<TxArea> getAllTxArea() ;

}

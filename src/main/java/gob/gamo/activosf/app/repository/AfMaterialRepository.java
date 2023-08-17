/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfMaterial;

/**
 *
 * @author wherrera
 */

public interface AfMaterialRepository extends JpaRepository<AfMaterial,Integer>{

    @Query("SELECT a FROM AfMaterial a WHERE a.estado = 'A' ORDER BY a.nombre")
	public List<AfMaterial> findAllActives() ;
    
    @Query("SELECT a.idMaterial "
    			+ " FROM AfKardexMaterial a "
    			+ " WHERE a.estado = 'A' "
    			+ " AND a.gestion = :gestion "
    			+ " AND a.idAlmacen = :idAlmacen "
    			+ " ORDER BY a.idMaterial.nombre")
                //ojooo param id class
    public List<AfMaterial> findAllActivesAfMaterialPorAfAlmacenYGestion(AfAlmacen afAlmacen, Integer gestion);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.AfAltaMaterial;

/**
 *
 * @author wherrera
 */

public interface AfAltaMaterialRepository extends JpaRepository<AfAltaMaterial, Integer>{
    @Query(value = "SELECT a FROM AfAltaMaterial a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.fechaAlta")
	public List<AfAltaMaterial> findAllActivesPorGestion(@Param("gestion")  Integer gestion) ;
}

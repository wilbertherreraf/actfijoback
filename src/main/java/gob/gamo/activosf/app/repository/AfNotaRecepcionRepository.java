/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfNotaRecepcion;

/**
 *
 * @author wherrera
 */

public interface AfNotaRecepcionRepository extends JpaRepository<AfNotaRecepcion, Integer> {
    @Query("SELECT a FROM AfNotaRecepcion a WHERE a.gestion = :gestion AND a.estado = 'A'")
    public List<AfNotaRecepcion> findAllActivesByGestion(Integer gestion);

    @Query("SELECT a FROM AfNotaRecepcion a WHERE a.gestion = :gestion AND a.estado = 'A' AND a.idNotaRecepcion > 0 ORDER BY a.idNotaRecepcion DESC")
    public List<AfNotaRecepcion> findAllActivesByGestionSinMigracion(Integer gestion);
}

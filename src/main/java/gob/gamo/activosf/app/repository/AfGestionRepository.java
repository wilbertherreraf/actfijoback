/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfGestion;

/**
 *
 * @author wherrera
 */

public interface AfGestionRepository extends JpaRepository<AfGestion, Integer>{

    @Query("SELECT a FROM AfGestion a WHERE a.vigente = :vigente AND a.estado = 'A'")
    public Optional<AfGestion> getAfGestionVigente(Boolean vigente) ;

    @Query("SELECT a FROM AfGestion a WHERE a.gestion IN ( SELECT max(ag.gestion) FROM AfGestion ag WHERE ag.estado = 'A')")
    public Optional<AfGestion> getMaxAfGestion();

    @Query("SELECT a FROM AfGestion a WHERE a.gestion = :gestion AND a.estado = 'A'")
    public AfGestion getByGestion(Integer gestion);
}

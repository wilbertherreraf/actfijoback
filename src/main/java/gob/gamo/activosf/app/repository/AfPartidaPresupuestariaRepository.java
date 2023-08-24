/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfPartidaPresupuestaria;

/**
 *
 * @author wherrera
 */
public interface AfPartidaPresupuestariaRepository extends JpaRepository<AfPartidaPresupuestaria, Integer> {
    @Query("SELECT a FROM AfPartidaPresupuestaria a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.codigo")
    public List<AfPartidaPresupuestaria> findAllActivesByGestion(Integer gestion);
}

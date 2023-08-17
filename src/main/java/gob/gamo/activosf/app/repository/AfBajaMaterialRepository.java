/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfBajaMaterial;

/**
 *
 * @author wherrera
 */

public interface AfBajaMaterialRepository extends JpaRepository<AfBajaMaterial, Integer> {
    @Query("SELECT a FROM AfBajaMaterial a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.idBajaMaterial DESC")
    public List<AfBajaMaterial> findAllActivesPorGestion(Integer gestion);

}

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

import gob.gamo.activosf.app.domain.AfFamiliaActivo;

/**
 *
 * @author wherrera
 */
public interface AfFamiliaActivoRepository extends JpaRepository<AfFamiliaActivo, Integer> {
    @Query("SELECT a FROM AfFamiliaActivo a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.codigo")
    public List<AfFamiliaActivo> findAllActivesByGestion(Integer gestion);

    @Query("SELECT a FROM AfFamiliaActivo a WHERE a.codigo = :codigo AND  a.gestion = :gestion AND a.estado = 'A'")
    public Optional<AfFamiliaActivo> findAfFamiliaActivoByCodigoAndGestion(String codigo, Integer gestion);
}

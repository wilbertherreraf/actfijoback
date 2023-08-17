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
import gob.gamo.activosf.app.domain.AfSubFamiliaActivo;

/**
 * @author wherrera
 */

public interface AfSubFamiliaActivoRepository extends JpaRepository<AfSubFamiliaActivo, Integer> {
    @Query("SELECT a FROM AfSubFamiliaActivo a WHERE a.gestion = :gestion AND a.estado = 'A' ORDER BY a.descripcion")
    public List<AfSubFamiliaActivo> findAllActivesByGestion(Integer gestion);

    @Query("SELECT a FROM AfSubFamiliaActivo a WHERE a.gestion = :gestion AND a.estado = 'A' AND a.idFamiliaActivo = :afFamiliaActivo ORDER BY a.codigo")
    public List<AfSubFamiliaActivo> findAllActivesByGestionAndAfFamiliaActivo(Integer gestion,
            AfFamiliaActivo afFamiliaActivo);

    @Query("SELECT a FROM AfSubFamiliaActivo a WHERE a.idFamiliaActivo = :idFamiliaActivo AND a.codigo = :codigo AND  a.gestion = :gestion AND a.estado = 'A'")
    public Optional<AfSubFamiliaActivo> findAfSubFamiliaActivoByCodigoAndGestion(AfFamiliaActivo afFamiliaActivo,
            String codigo, Integer gestion);

}

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

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfMaterial;

/**
 *
 * @author wherrera
 */
public interface AfKardexMaterialRepository extends JpaRepository<AfKardexMaterial, Integer> {
    @Query(
            "SELECT a FROM AfKardexMaterial a WHERE a.estado = 'A' AND a.gestion = :gestion AND a.idMaterial = :idMaterial AND a.idAlmacen = :idAlmacen")
    public Optional<AfKardexMaterial> findActiveByGestionAndMaterialAndAlmacen(
            Integer gestion, AfMaterial idMaterial, AfAlmacen idAlmacen);

    @Query("SELECT a FROM AfKardexMaterial a WHERE a.estado = 'A' AND a.gestion = :gestion")
    public List<AfKardexMaterial> findAllActivesByGestion(Integer gestion);

    @Query(
            "SELECT a.idMaterial FROM AfKardexMaterial a WHERE a.estado = 'A' AND a.saldoCantidad > 0 AND a.gestion = :gestion")
    public List<AfMaterial> getAfMaterialConSaldoCantidadDisponiblePorGestion(Integer gestion);

    @Query(
            "SELECT a FROM AfKardexMaterial a WHERE a.estado = 'A' AND a.gestion = :gestion AND a.idMaterial IN :materialList ORDER BY a.idMaterial")
    // ojoo lista pearam
    public List<AfKardexMaterial> findByAfMaterialListAndGestion(List<AfMaterial> materialList, Integer gestion);

    @Query(" SELECT a "
            + " FROM AfKardexMaterial a "
            + " WHERE a.estado = 'A' "
            + " AND a.gestion = :gestion "
            + " AND a.saldoCantidad > 0 "
            + " AND a.idMaterial IN :materialList "
            + " ORDER BY a.idMaterial")
    public List<AfKardexMaterial> findByAfMaterialListAndGestionConSaldo(
            List<AfMaterial> materialList, Integer gestion);
}

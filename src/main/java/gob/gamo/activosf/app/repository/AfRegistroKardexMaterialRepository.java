/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;

/**
 *
 * @author wherrera
 */
public interface AfRegistroKardexMaterialRepository extends JpaRepository<AfRegistroKardexMaterial, Integer> {

    @Query(
            "SELECT a FROM AfRegistroKardexMaterial a WHERE a.estado = 'A' AND a.idKardexMaterial = :afKardexMaterial ORDER BY  a.fechaRegistro")
    public List<AfRegistroKardexMaterial> findAllActivesByAfKardexMaterial(AfKardexMaterial afKardexMaterial);

    @Query(" SELECT a FROM AfRegistroKardexMaterial a "
            + " WHERE a.estado = 'A' "
            + " AND a.saldo > 0 "
            + " AND a.idKardexMaterial = :afKardexMaterial "
            + " AND a.idKardexMaterial.gestion = :gestion "
            + "	AND a.catTipoRegistroKardex IN ('SALINI', 'INGADQ', 'INGDEV', 'INGACC') "
            + " ORDER BY a.fechaRegistro ")
    public List<AfRegistroKardexMaterial> findIngresoInicialConSaldoByAfKardexMaterialPorGestionOrdenadoPeps(
            AfKardexMaterial afKardexMaterial, Integer gestion);
}

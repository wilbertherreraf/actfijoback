/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfTipoCambio;

/**
 *
 * @author wherrera
 */

public interface AfTipoCambioRepository extends JpaRepository<AfTipoCambio, Integer> {

    @Query("SELECT a FROM AfTipoCambio a WHERE a.fecha = :fecha AND a.catMoneda = :catMoneda AND a.estado = 'A'")
    public Optional<AfTipoCambio> getAfTipoCambioByCatMonedaAndFecha(String catMoneda, Date fecha);

    @Query("SELECT a FROM AfTipoCambio a WHERE a.catMoneda = :catMoneda AND a.estado = 'A' ORDER BY a.fecha desc")
    public Optional<AfTipoCambio> getAfTipoCambioUltimo(String catMoneda);

}

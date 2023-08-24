/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.TxTransaccion;

/**
 *
 * @author wherrera
 */
public interface TxTransaccionRepository extends JpaRepository<TxTransaccion, Integer> {}

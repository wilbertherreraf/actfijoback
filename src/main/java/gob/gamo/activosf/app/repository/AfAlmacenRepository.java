/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.gamo.activosf.app.domain.AfAlmacen;

/**
 *
 * @author wherrera
 */

public interface AfAlmacenRepository extends JpaRepository<AfAlmacen, Integer>{


}

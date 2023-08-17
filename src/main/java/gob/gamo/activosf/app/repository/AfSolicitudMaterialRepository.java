/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.gamo.activosf.app.domain.AfSolicitudMaterial;

/**
 *
 * @author wherrera
 */

public interface AfSolicitudMaterialRepository extends JpaRepository<AfSolicitudMaterial,Integer>{

}

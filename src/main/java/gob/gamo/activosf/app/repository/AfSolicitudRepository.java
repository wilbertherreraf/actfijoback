/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gob.gamo.activosf.app.domain.AfSolicitud;
import gob.gamo.activosf.app.domain.TxUsuario;

/**
 *
 * @author wherrera
 */

public interface AfSolicitudRepository extends JpaRepository<AfSolicitud, Integer> {

    @Query("SELECT a "
            + " FROM AfSolicitud a "
            + " WHERE a.estado = 'A' "
            + " AND a.gestion = :gestion "
            + " AND a.catTipoSolicitud = :catTipoSolicitud "
            + " AND  ( a.idUsuarioAutorizacion = :idUsuario "
            + "	OR a.idUsuarioSolicitud = :idUsuario) "
            + " ORDER BY a.fechaSolicitud desc")
    public List<AfSolicitud> findAllActivesByGestionAndCatTipoSolicitudYUsuario(Integer gestion,
            String catTipoSolicitud, TxUsuario idUsuario);

    @Query("SELECT a "
            + " FROM AfSolicitud a "
            + " WHERE a.estado = 'A' "
            + " AND a.gestion = :gestion "
            + " AND a.catTipoSolicitud = :catTipoSolicitud "
            + " ORDER BY a.fechaSolicitud desc")
    public List<AfSolicitud> findAllActivesByGestionAndCatTipoSolicitud(Integer gestion, String catTipoSolicitud);
}

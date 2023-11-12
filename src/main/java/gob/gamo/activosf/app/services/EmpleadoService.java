package gob.gamo.activosf.app.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.utils.UtilsDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository repositoryEntity;
    private final PersonaService personaService;
    private final UnidadService unidadService;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<OrgEmpleado> findAll(Pageable pageable) {

        Page<OrgEmpleado> list = repositoryEntity.findAll(pageable);
        return list;
    }

    @Transactional
    public OrgEmpleado crearNuevo(OrgEmpleado entity) {
        if (entity.getId() != null) new DataException("Entidad con id, debe ser nulo");

        validar(entity);
        OrgPersona persona = personaService.findById(entity.getIdPersona());
        OrgUnidad unidad = unidadService.findById(entity.getIdUnidad());

        PersonaService.validar(persona);
        UnidadService.validar(unidad);

        boolean isPersona = persona.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
        if (!isPersona) {
            throw new DataException("Persona (" + persona.getNombre() + ") debe ser natural");
        }

        Optional<OrgEmpleado> empOld = empleadoActivo(entity.getIdPersona());
        if (empOld.isPresent()) {
            throw new DataException(
                    "Persona se encuentra activo como empleado " + empOld.get().getId() + " no puede continuar.");
        }

        Optional<OrgEmpleado> unidBoss = empleadoBoss(entity.getIdUnidad(), true);
        validarNewUnidad(entity, unidad, unidBoss);

        OrgEmpleado newEntity = repositoryEntity.save(entity);

        updUnidadBoss(newEntity);

        log.info("entitiy creado {}", newEntity.getId());
        return newEntity;
    }

    @Transactional
    public OrgEmpleado update(OrgEmpleado entityNew) {
        if (entityNew.getId() == null) throw new DataException("Empleado con id nulo");

        validar(entityNew);
        OrgPersona p = personaService.findById(entityNew.getIdPersona());
        OrgUnidad unidadNew = unidadService.findById(entityNew.getIdUnidad());

        OrgEmpleado empleadoOld = findById(entityNew.getId());

        if (empleadoOld.getFechaBaja() != null) {
            throw new DataException("Empleado ya fue dado de baja: " + empleadoOld.getId());
        }

        PersonaService.validar(p);
        UnidadService.validar(unidadNew);

        boolean isPersona = p.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
        if (!isPersona) {
            throw new DataException("Persona (" + p.getNombre() + ") debe ser natural");
        }

        Optional<OrgEmpleado> unidBossOld = empleadoBoss(empleadoOld.getIdUnidad(), true);

        validarOldUnidad(empleadoOld, entityNew, unidBossOld);

        if (unidBossOld.isPresent()) {
            if (unidBossOld.get().getId().compareTo(empleadoOld.getId()) != 0
                    && empleadoOld.getUnidad().getRolempleado().compareTo(entityNew.getRolempleado()) == 0) {
                // change of boss
                Date fBaja = UtilsDate.compara(
                                        entityNew.getFechaIngreso(),
                                        unidBossOld.get().getFechaIngreso())
                                >= 0
                        ? entityNew.getFechaIngreso()
                        : unidBossOld.get().getFechaIngreso();
                bajaEmpleado(unidBossOld.get().getId(), fBaja, false);
            }
        }

        Optional<OrgEmpleado> unidBossNew = empleadoBoss(entityNew.getIdUnidad(), true);
        validarNewUnidad(entityNew, unidadNew, unidBossNew);

        empleadoOld.setCodInternoempl(entityNew.getCodInternoempl());
        empleadoOld.setCodPersona(entityNew.getCodPersona());
        empleadoOld.setEstado(entityNew.getEstado());
        empleadoOld.setFechaIngreso(entityNew.getFechaIngreso());
        empleadoOld.setIdCargo(entityNew.getIdCargo());
        empleadoOld.setIdEmpleadopadre(entityNew.getIdEmpleadopadre());
        empleadoOld.setIdUnidad(entityNew.getIdUnidad());
        // empleadoOld.setIdPersona(entityNew.getIdPersona());
        empleadoOld.setTabRolempleado(entityNew.getTabRolempleado());
        empleadoOld.setRolempleado(entityNew.getRolempleado());
        empleadoOld.setIdCargo(entityNew.getIdCargo());

        OrgEmpleado newEntity = repositoryEntity.save(empleadoOld);
        updUnidadBoss(newEntity);

        return newEntity;
    }

    @Transactional
    public void bajaEmpleado(Integer id, Date fechaBaja, boolean verificaUnidBoss) {
        OrgEmpleado e = findById(id);
        if (e.getFechaBaja() != null) {
            throw new DataException("Empleado ya fue dado de baja");
        }
        if (fechaBaja == null) {
            throw new DataException("Operación requiere fecha de baja");
        }

        if (e.getFechaIngreso() != null && UtilsDate.compara(e.getFechaIngreso(), fechaBaja) > 0) {
            throw new DataException("Fecha ingreso mayor a fecha baja");
        }

        boolean isBoss = false;
        Optional<OrgEmpleado> unidBossOld = empleadoBoss(e.getIdUnidad(), false);
        if (unidBossOld.isPresent()) {
            isBoss = unidBossOld.get().getId().compareTo(id) == 0;
            if (verificaUnidBoss && isBoss) {
                throw new DataException("Empleado asignado con rol Principal en unidad.");
            }

            if (isBoss) {
                // change of boss
                log.info("Change of boss unidad {} from {} to {}", e.getIdUnidad(), e.getId(), "NULL");
                unidadService.updateIdEmpleado(e.getIdUnidad(), null);
            }
        }
        e.setFechaBaja(fechaBaja);
        repositoryEntity.save(e);
    }

    @Transactional
    public void updUnidadBoss(OrgEmpleado entityNew) {
        OrgUnidad unidadNew = unidadService.findById(entityNew.getIdUnidad());
        if (unidadNew.getIdEmpleado() == null) {
            Optional<OrgEmpleado> boss = empleadoBoss(entityNew.getIdUnidad(), false);
            if (boss.isPresent()) {
                unidadService.updateIdEmpleado(
                        unidadNew.getIdUnidad(), boss.get().getId());
            }
        } else {
            if (unidadNew.getRolempleado().compareTo(entityNew.getRolempleado()) == 0) {
                unidadService.updateIdEmpleado(unidadNew.getIdUnidad(), entityNew.getId());
            }
        }
    }

    @Transactional
    public void eliminarEmpleado(Integer id) {
        OrgEmpleado e = findById(id);
        e.setFechaBaja(new Date());
        repositoryEntity.save(e);
    }

    public OrgEmpleado findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new DataException("Empleado inexistente : `%s`".formatted(id)));
    }

    public Optional<OrgEmpleado> empleadoActivo(Integer idPersona) {
        List<OrgEmpleado> list = repositoryEntity.findByIdPersonaActivo(idPersona);
        if (list.size() == 1) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    public OrgEmpleado findByIdAct(Integer idEmpl) {
        OrgEmpleado emp = repositoryEntity
                .findByIdAct(idEmpl)
                .orElseThrow(() -> new DataException("Empleado inexistente : `%s`".formatted(idEmpl)));
        return emp;
    }

    public OrgEmpleado empleadoIsActivo(Integer idPersona) {
        return empleadoActivo(idPersona)
                .orElseThrow(() -> new DataException("Persona " + idPersona + " inexistente o no se encuentra activo"));
    }

    public Optional<OrgEmpleado> empleadoBoss(Integer idUnidad, boolean verifica) {
        Optional<OrgEmpleado> boss = repositoryEntity.empleadoBoss(idUnidad);

        if (boss.isPresent()) {
            return boss;
        }
        // OrgUnidad unidad = unidadService.findById(idUnidad);
        List<OrgEmpleado> l = repositoryEntity.empleadosBoss(idUnidad);

        if (l.size() > 1 && verifica) {
            throw new DataException("Unidad " + idUnidad + " con mas de un empleado activo con rol "
                    + l.get(0).getRolempleadodesc().getDesDescrip());
        }

        if (l.size() > 0) {
            return Optional.of(l.get(0));
        }

        return Optional.empty();
    }

    public List<OrgEmpleado> empleadosUnidad(Integer idUnidad) {
        List<OrgEmpleado> l = repositoryEntity.empleadosUnidad(idUnidad);
        return l;
    }

    public OrgUnidad retUnidad(User me, Integer idEmpleado, Integer idUnidad) {
        OrgEmpleado empl = empleadoActivo(me.getIdUnidEmpl())
                .orElseThrow(() ->
                        new DataException("ID Persona de usuario inexistente en empleados " + me.getIdUnidEmpl()));
        if (empl.getIdUnidad() == null || empl.getIdUnidad() != idUnidad) {
            throw new DataException("Operacion: Empleado difiere con la Id de la operacion " + idEmpleado);
        }
        return empl.getUnidad();
    }

    public void validar(OrgEmpleado e) {
        if (e.getIdPersona() == null) {
            throw new DataException("Id Persona requerido");
        }
        if (e.getIdUnidad() == null) {
            throw new DataException("Id Unidad requerido");
        }
        if (e.getFechaIngreso() == null) {
            throw new DataException("Fecha ingreso requerido");
        }
        if (e.getRolempleado() == null) {
            throw new DataException("Rol empleado requerido");
        }
    }

    public void validarOldUnidad(OrgEmpleado empleadoOld, OrgEmpleado entityNew, Optional<OrgEmpleado> unidBoss) {
        OrgUnidad unidOld = empleadoOld.getUnidad();
        // Optional<OrgEmpleado> unidBoss = empleadoBoss(entity.getIdUnidad(), true);
        if (unidBoss.isPresent()) {
            if (entityNew.getId() != null
                    && entityNew.getId().compareTo(unidBoss.get().getId()) == 0) {
                if (unidOld != null && unidOld.getRolempleado().compareTo(entityNew.getRolempleado()) != 0) {
                    throw new DataException("Empleado asignado con rol Principal "
                            + unidOld.getRolempleadodesc().getDesDescrip()
                            + " de la unidad " + unidOld.getIdUnidad() + " - " + unidOld.getNombre()
                            + " primero asigne nuevo personal principal.");
                }

                if (entityNew.getIdUnidad().compareTo(unidBoss.get().getIdUnidad()) != 0) {
                    throw new DataException("Empleado asignado con rol Principal "
                            + unidBoss.get().getUnidad().getRolempleadodesc().getDesDescrip()
                            + " de la unidad " + unidBoss.get().getIdUnidad()
                            + ". Designe primero uno nuevo a la unidad con rol principal.");
                }
            }
        }
    }

    public void validarNewUnidad(OrgEmpleado entityNew, OrgUnidad unidadNew, Optional<OrgEmpleado> unidBoss) {
        if (unidBoss.isPresent()) {
            if (unidadNew.getIdEmpleado() != null
                    && entityNew.getId() != null
                    && unidadNew.getIdEmpleado().compareTo(entityNew.getId()) != 0) {
                // not is boss
                if (unidadNew.getRolempleado().compareTo(entityNew.getRolempleado()) == 0) {
                    throw new DataException("Unidad ya tiene asignado empleado con rol "
                            + unidBoss.get().getRolempleadodesc().getDesDescrip()
                            + " no puede continuar.");
                }
            }

            if (entityNew.getId() == null) {
                if (unidadNew.getRolempleado().compareTo(entityNew.getRolempleado()) == 0) {
                    throw new DataException("Unidad ya tiene asignado empleado con rol "
                            + unidBoss.get().getRolempleadodesc().getDesDescrip()
                            + " no puede continuar.");
                }
            }

        } else {
            if (unidadNew.getRolempleado().compareTo(entityNew.getRolempleado()) != 0) {
                throw new DataException("Unidad " + unidadNew.getIdUnidad() + " No tiene asignado empleado con rol "
                        + unidadNew.getRolempleadodesc().getDesDescrip()
                        + " no puede continuar.");
            }
        }
    }
}

package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import gob.gamo.activosf.app.dto.EmpleadoVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchQueryCriteriaConsumer;
import gob.gamo.activosf.app.utils.UtilsDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
        if (entity.getId() != null)
            new DataException("Entidad con id, debe ser nulo");

        validar(entity);
        OrgPersona p = personaService.findById(entity.getIdPersona());
        OrgUnidad u = unidadService.findById(entity.getIdUnidad());

        PersonaService.validar(p);
        UnidadService.validar(u);
        boolean isPersona = p.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
        if (!isPersona) {
            throw new DataException(
                    "Persona (" + p.getNombre() + ") debe ser natural");
        }

        Optional<OrgEmpleado> empOld = empleadoActivo(entity.getIdPersona());
        if (empOld.isPresent()) {
            throw new DataException("Persona se encuentra activo " + empOld.get().getId() + " no puede continuar.");
        }
        Optional<OrgEmpleado> unidBoss = empleadoBoss(entity.getIdUnidad(), true);
        if (unidBoss.isPresent()) {
            if (unidBoss.get().getRolempleado().compareTo(entity.getRolempleado()) == 0) {
                throw new DataException("Unidad ya tiene asignado empleado con rol "
                        + unidBoss.get().getRolempleadodesc().getDesDescrip()
                        + " no puede continuar.");
            }
        } else {
            if (u.getRolempleado().compareTo(entity.getRolempleado()) != 0) {
                throw new DataException(
                        "Unidad No tiene asignado empleado con rol " + u.getRolempleadodesc().getDesDescrip()
                                + " no puede continuar.");
            }
        }
        // suspenderTodo(entity.getIdPersona());

        OrgEmpleado newEntity = repositoryEntity.save(entity);
        log.info("entitiy creado {}", newEntity.getId());
        return newEntity;
    }

    @Transactional
    public OrgEmpleado update(OrgEmpleado entity) {
        if (entity.getId() == null || entity.getId().compareTo(0) == 0)
            throw new DataException("Entidad con id, debe nulo");

        validar(entity);
        OrgPersona p = personaService.findById(entity.getIdPersona());
        OrgUnidad u = unidadService.findById(entity.getIdUnidad());

        PersonaService.validar(p);
        UnidadService.validar(u);

        boolean isPersona = p.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
        if (!isPersona) {
            throw new DataException(
                    "Persona (" + p.getNombre() + ") debe ser natural");
        }

        Optional<OrgEmpleado> unidBoss = empleadoBoss(entity.getIdUnidad(), true);
        if (unidBoss.isPresent()) {
            if (entity.getIdPersona().compareTo(unidBoss.get().getIdPersona()) != 0
                    && unidBoss.get().getRolempleado().compareTo(entity.getRolempleado()) == 0) {
                throw new DataException("Unidad ya tiene asignado empleado con rol "
                        + unidBoss.get().getRolempleadodesc().getDesDescrip()
                        + " no puede continuar.");
            }
        } else {
            if (u.getRolempleado().compareTo(entity.getRolempleado()) != 0) {
                throw new DataException(
                        "Unidad No tiene asignado empleado con rol Principal " + u.getRolempleadodesc().getDesDescrip()
                                + " no puede continuar.");
            }
        }
        OrgEmpleado eold = findById(entity.getId());
        /*
         * eold.setFechaIngreso(entity.getFechaIngreso());
         * eold.setCodInternoempl(entity.getCodInternoempl());
         */
        if (eold.getFechaBaja() != null) {
            throw new DataException("Empleado ya fue dado de baja");
        }
        log.info("fecha ingreso: {} [{}]", UtilsDate.stringFromDate(entity.getFechaIngreso(), "dd/MM/yyyy"),
                entity.getFechaIngreso());
        OrgEmpleado newEntity = repositoryEntity.save(entity);
        return newEntity;
    }

    @Transactional
    public void delete(Integer id, EmpleadoVo entityReq) {

        OrgEmpleado e = findById(id);
        if (e.getFechaBaja() != null) {
            throw new DataException("Empleado ya fue dado de baja");
        }
        if (entityReq.fechaBaja() == null) {
            throw new DataException("Operación requiere fecha de baja");
        }
        ;
        if (e.getFechaIngreso() != null && UtilsDate.compara(e.getFechaIngreso(), entityReq.fechaBaja()) > 0) {
            throw new DataException("Fecha ingreso mayor a fecha baja");
        }
        e.setFechaBaja(entityReq.fechaBaja());
        repositoryEntity.save(e);

        // repositoryEntity.deleteById(id);
    }

    public OrgEmpleado findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro not found : `%s`".formatted(id)));
    }

    public OrgEmpleado nuevo(OrgEmpleado e) {
        return e.builder().codInternoempl(null).build();
    }

    public Optional<OrgEmpleado> getByIdPersonaActivo(Integer idPersona, Integer idUnidad, Integer rolempleado) {
        Optional<OrgEmpleado> empOld = empleadoActivo(idPersona);
        List<OrgEmpleado> list = repositoryEntity.findByIdPersonaActivo(idPersona);
        if (list.size() > 1) {

        }
        for (OrgEmpleado e : list) {

        }
        return null;
    }

    public Optional<OrgEmpleado> empleadoActivo(Integer idPersona) {
        List<OrgEmpleado> list = repositoryEntity.findByIdPersonaActivo(idPersona);
        if (list.size() > 1) {
            throw new DataException(
                    "Empleado registrado activo mas de una vez  " + list.get(0).getId());
        }
        if (list.size() > 0) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    public Optional<OrgEmpleado> empleadoBoss(Integer idUnidad, boolean verifica) {
        List<OrgEmpleado> l = repositoryEntity.empleadosBoss(idUnidad);
        if (l.size() > 1 && verifica) {
            throw new DataException(
                    "Unidad " + idUnidad + " con mas de un empleado con rol "
                            + l.get(0).getRolempleadodesc().getDesDescrip());
        }
        if (l.size() == 1) {
            return Optional.of(l.get(0));
        }
        return Optional.empty();
    }

    public List<OrgEmpleado> empleadosUnidad(Integer idUnidad) {
        List<OrgEmpleado> l = repositoryEntity.empleadosUnidad(idUnidad);
        return l;
    }

    public void suspenderTodo(Integer idPersona) {
        List<OrgEmpleado> l = repositoryEntity.findAllByIdPersona(idPersona);
        for (OrgEmpleado orgEmpleado : l) {
            if (orgEmpleado.getFechaBaja() == null) {
                orgEmpleado.setFechaBaja(new Date());
                repositoryEntity.save(orgEmpleado);
            }
        }
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

    public List<OrgEmpleado> search(SearchCriteria params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<OrgEmpleado> root = query.from(OrgEmpleado.class);
        Join<OrgEmpleado, OrgPersona> joinPersona = root.join("persona", JoinType.INNER);

/*         Join<OrgEmpleado,OrgPersona> joinPersona = root.join("empleado", JoinType.INNER);
        Join<OrgPersona,User> joinUsuario = root.join("user", JoinType.INNER);    */
        
        query.multiselect(root, joinPersona); // root.get("nombre")

        Predicate predicate = builder.conjunction();

        SearchQueryCriteriaConsumer<OrgEmpleado> searchConsumer = new SearchQueryCriteriaConsumer<OrgEmpleado>(
                predicate,
                builder, root);

        searchConsumer.accept(params);
        Predicate predicateR = searchConsumer.getPredicate();
        query.where(predicateR);

        List<Tuple> l = entityManager.createQuery(query).getResultList();

        return l.stream().map(r -> {
            OrgEmpleado e = (OrgEmpleado) r.get(0);
            return e;
        }).map((x) -> x).toList();
    }
    /*
     * public OrgEmpleado existsOrgEmpleado(String id) {
     * return repositoryEntity
     * .findBy Sigla(id)
     * .orElseThrow(() -> new
     * NoSuchElementException("Registro not found : `%s`".formatted(id)));
     * }
     */
}

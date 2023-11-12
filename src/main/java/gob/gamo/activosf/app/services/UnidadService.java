package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.UserSpecification;
import gob.gamo.activosf.app.treeorg.Node;
import gob.gamo.activosf.app.treeorg.OrgHierarchy;
import gob.gamo.activosf.app.treeorg.OrgHierarchyInterface;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadService {
    private final OrgUnidadRepository repositoryEntity;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public UnidadResponse crearNuevo(OrgUnidad entity) {
        if (entity.getIdUnidad() != null) new DataException("Entidad con id, debe ser nulo");

        OrgUnidad newEntity = null;
        if (entity.getIdUnidadPadre() == null) {
            newEntity = repositoryEntity.save(entity);
        } else {
            OrgUnidad undPadreOld = findById(entity.getIdUnidadPadre());
            newEntity = repositoryEntity.save(entity);
        }

        log.info("entitiy creado {}", newEntity.getIdUnidad());
        return new UnidadResponse(newEntity);
    }

    @Transactional
    public UnidadResponse update(UnidadResponse entityReq) {
        if (entityReq.id() == null) new DataException("Entidad con id nulo");

        OrgUnidad entity = OrgUnidad.createOrgUnidad(entityReq);
        OrgHierarchyInterface<OrgUnidad> orgTree = generarOrgTree(entity);

        OrgUnidad newEntity = null;

        OrgUnidad undOld = findById(entity.getIdUnidad());

        if (orgTree.isEmpty() || entity.getIdUnidadPadre() == null) {
            newEntity = repositoryEntity.save(entity);
        } else {
            if (entity.getIdUnidad().compareTo(entity.getIdUnidadPadre()) == 0) {
                throw new DataException(
                        "Padre " + entity.getIdUnidadPadre() + " debe ser diferente a " + entity.getIdUnidad());
            }
            OrgUnidad undPadreOld = findById(entity.getIdUnidadPadre());

            Node<OrgUnidad> found = orgTree.searchInTree(entity.getIdUnidadPadre());

            if (orgTree.size() > 1 && found != null) {
                orgTree.updateParent(entity.getIdUnidad(), entity.getIdUnidadPadre());
                orgTree = (OrgHierarchy<OrgUnidad>) generarOrgTree(entity);
            }
            newEntity = repositoryEntity.save(entity);
        }

        return new UnidadResponse(newEntity);
    }

    @Transactional
    public void updatePadre(Integer idOld, Integer idPadreNew) {

        OrgUnidad undOld = findById(idOld);
        OrgUnidad undNewPadre = findById(idPadreNew);

        List<OrgUnidad> hijos = getHijosN1(undOld);
        if (hijos.size() == 0) {
            throw new DataException("Registro " + idOld + " sin hijos");
        }

        if (idOld.compareTo(idPadreNew) == 0) {
            throw new DataException("Nuevo padre " + idPadreNew + " debe ser diferente al anterior " + idOld);
        }

        OrgHierarchyInterface<OrgUnidad> orgTree = generarOrgTree(undOld);
        orgTree.replaceWithOtherNode(idOld, idPadreNew);

        for (OrgUnidad ou : hijos) {
            ou.setIdUnidadPadre(idPadreNew);
        }
        repositoryEntity.saveAll(hijos);
    }

    @Transactional
    public void updateIdEmpleado(Integer idUnidad, Integer idEmpleado) {
        if (idEmpleado == null) {
            // throw new DataException("Unidad: Id empleado principal nulo.");
        }

        OrgUnidad undOld = findById(idUnidad);
        undOld.setIdEmpleado(idEmpleado);

        repositoryEntity.save(undOld);
    }

    @Transactional
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
    }

    public static void validar(OrgUnidad entity) {
        if (entity.getNombre() == null) {
            throw new DataException("Unidad: Nombre requerido");
        }
        if (entity.getRolempleado() == null) {
            throw new DataException("Unidad: Rol de empleado principal requerido");
        }
    }

    public List<OrgUnidad> getHijosN1(OrgUnidad entity) {
        List<OrgUnidad> list = repositoryEntity.findAll();
        List<OrgUnidad> values = hijos(list, entity.getIdUnidad());
        return values;
    }

    public List<OrgUnidad> getHijos(OrgUnidad entity) {
        OrgHierarchyInterface<OrgUnidad> orgTree = generarOrgTree(entity);
        Node<OrgUnidad> found = orgTree.searchInTree(entity.getIdUnidad());
        if (found == null) {
            return new ArrayList<>();
        }
        List<OrgUnidad> values = orgTree.returnChildrens(found).values().stream()
                .map(x -> x.getValue())
                .filter(x -> x.getIdUnidad().compareTo(entity.getIdUnidad()) != 0)
                .collect(Collectors.toList());
        return values;
    }

    public OrgUnidad findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Unidad inexistente : `%s`".formatted(id)));
    }

    @Transactional(readOnly = true)
    public OrgHierarchyInterface<OrgUnidad> generarOrgTree(OrgUnidad entity) {
        List<OrgUnidad> list = repositoryEntity.findAll();

        return generarOrgTree(list, entity);
    }

    public static OrgHierarchyInterface<OrgUnidad> generarOrgTreeFromList(List<OrgUnidad> list) {
        List<OrgUnidad> listWithoutOrphan = list.stream()
                .filter(n -> n.getIdUnidadPadre() != null
                        || (n.getChildren() != null && n.getChildren().size() > 0))
                .toList();

        return generarOrgTree(list, (listWithoutOrphan.size() > 0 ? listWithoutOrphan.get(0) : null));
    }

    public static OrgHierarchyInterface<OrgUnidad> generarOrgTree(List<OrgUnidad> list, OrgUnidad orgUnidad) {
        OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
        if (list.size() == 0 || orgUnidad == null) {
            return org;
        }

        Integer idNodeCurr = orgUnidad.getIdUnidad();

        OrgUnidad root = searchRoot(list, orgUnidad, idNodeCurr);
        if (root == null) {
            throw new DataException("Registro padre inexistente para unidad: " + idNodeCurr);
        }
        log.info("Root lista {}", root.getIdUnidad());
        org.hireOwner(new Node<OrgUnidad>(root.getIdUnidad(), root));

        List<OrgUnidad> hijosRoot = hijos(list, root.getIdUnidad());

        for (OrgUnidad ou : hijosRoot) {
            org.addNewChild(createNode(ou), ou.getIdUnidadPadre());
            // log.info("Root OU: {} padre: {} ", ou.getIdUnidad(), ou.getIdUnidadPadre());
            addHijos(list, org, ou.getIdUnidad());
        }
        return org;
    }

    public static OrgUnidad searchRoot(List<OrgUnidad> list, OrgUnidad node, Integer idpadre) {
        if (idpadre == null) {
            return node;
        }

        Optional<OrgUnidad> oupadre = list.stream()
                .filter(r -> r.getIdUnidad() != null && r.getIdUnidad().compareTo(idpadre) == 0)
                .findFirst();
        if (!oupadre.isPresent()) {
            return node;
        }

        if (oupadre.get().getIdUnidadPadre() != null) {
            OrgUnidad found = searchRoot(list, oupadre.get(), oupadre.get().getIdUnidadPadre());
            return found;
        }

        return oupadre.get();
    }

    public static Node<OrgUnidad> createNode(OrgUnidad orgUnidad) {
        return new Node<OrgUnidad>(orgUnidad.getIdUnidad(), orgUnidad);
    }

    public static void addHijos(List<OrgUnidad> list, OrgHierarchyInterface<OrgUnidad> org, Integer idUnidad) {
        List<OrgUnidad> hijosRoot = hijos(list, idUnidad);
        for (OrgUnidad ou : hijosRoot) {
            org.addNewChild(createNode(ou), ou.getIdUnidadPadre());
            addHijos(list, org, ou.getIdUnidad());
        }
    }

    public static List<OrgUnidad> hijos(List<OrgUnidad> list, Integer idpadre) {
        return list.stream()
                .filter(x -> x.getIdUnidad().compareTo(idpadre) != 0)
                .filter(r -> idpadre != null
                        && r.getIdUnidadPadre() != null
                        && r.getIdUnidadPadre().compareTo(idpadre) == 0)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Page<UnidadResponse> findAll(String searchTxt, Pageable pageable) {
        if (!StringUtils.isBlank(searchTxt)) {
            CriteriaParser parser = new CriteriaParser();
            Deque<?> deque = parser.parse(searchTxt);
            if (deque.size() > 0) {
                GenericSpecificationsBuilder<OrgUnidad> specBuilder = new GenericSpecificationsBuilder<>();
                Specification<OrgUnidad> spec = specBuilder.build(deque, UserSpecification::new);
                Page<UnidadResponse> list0 =
                        repositoryEntity.findAll(spec, pageable).map(r -> new UnidadResponse(r));
                return list0;
            }
        }
        Page<UnidadResponse> list = repositoryEntity.findAll(pageable).map(r -> new UnidadResponse(r));
        return list;
    }
}

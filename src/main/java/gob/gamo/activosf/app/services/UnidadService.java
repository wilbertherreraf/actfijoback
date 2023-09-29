package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.domain.OrgPersona;
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

    @Transactional(readOnly = true)
    public Page<UnidadResponse> findAll(String searchTxt, Pageable pageable) {
        CriteriaParser parser = new CriteriaParser();
        Deque<?> deque = parser.parse(searchTxt);
        if (deque.size() > 0) {
            GenericSpecificationsBuilder<OrgUnidad> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<OrgUnidad> spec = specBuilder.build(deque, UserSpecification::new);
            Page<UnidadResponse> list0 = repositoryEntity.findAll(spec,pageable).map(r -> new UnidadResponse(r));
            return list0;
        }
        Page<UnidadResponse> list = repositoryEntity.findAll(pageable).map(r -> new UnidadResponse(r));
        return list;
    }

    @Transactional
    public UnidadResponse crearNuevo(OrgUnidad entity) {
        if (entity.getIdUnidad() != null)
            new DataException("Entidad con id, debe ser nulo");

        OrgUnidad newEntity = null;
        if (entity.getIdUnidadPadre() == null) {
            newEntity = repositoryEntity.save(entity);
        } else {
            OrgUnidad undPadreOld = repositoryEntity.findById(entity.getIdUnidadPadre())
                    .orElseThrow(() -> new DataException("Registro Padre inexistente " + entity.getIdUnidadPadre()));
            newEntity = repositoryEntity.save(entity);
        }

        log.info("entitiy creado {}", newEntity.getIdUnidad());
        return new UnidadResponse(newEntity);
    }

    @Transactional
    public UnidadResponse update(UnidadResponse entityReq) {
        if (entityReq.id() == null || entityReq.id().compareTo(0) == 0)
            new DataException("Entidad con id, debe nulo");

        OrgUnidad entity = OrgUnidad.createOrgUnidad(entityReq);
        OrgHierarchyInterface<OrgUnidad> orgTree = generarOrgTree(entity);

        OrgUnidad newEntity = null;

        OrgUnidad undOld = repositoryEntity.findById(entity.getIdUnidad())
                .orElseThrow(() -> new DataException("Registro inexistente " + entity.getIdUnidad()));

        if (orgTree.isEmpty() || entity.getIdUnidadPadre() == null) {
            newEntity = repositoryEntity.save(entity);
        } else {
            if (entity.getIdUnidad().compareTo(entity.getIdUnidadPadre()) == 0) {
                throw new DataException(
                        "Padre " + entity.getIdUnidadPadre() + " debe ser diferente a " + entity.getIdUnidad());
            }
            OrgUnidad undPadreOld = repositoryEntity.findById(entity.getIdUnidadPadre())
                    .orElseThrow(() -> new DataException("Registro inexistente " + entity.getIdUnidadPadre()));

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

        OrgUnidad undOld = repositoryEntity.findById(idOld)
                .orElseThrow(() -> new DataException("Registro inexistente " + idOld));
        OrgUnidad undNewPadre = repositoryEntity.findById(idPadreNew)
                .orElseThrow(() -> new DataException("Registro inexistente " + idPadreNew));

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
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
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
        List<OrgUnidad> values = orgTree.returnChildrens(found).values().stream().map(x -> x.getValue())
        .filter(x -> x.getIdUnidad().compareTo(entity.getIdUnidad()) != 0)
                .collect(Collectors.toList());
        return values;
    }

    @Transactional(readOnly = true)
    public OrgHierarchyInterface<OrgUnidad> generarOrgTree(OrgUnidad entity) {
        List<OrgUnidad> list = repositoryEntity.findAll();
        return generarOrgTree(list, entity);
    }

    private OrgUnidad findById(String id) {
        return repositoryEntity
                .findBySigla(id)
                .orElseThrow(() -> new NoSuchElementException("Article not found : `%s`".formatted(id)));
    }

    public OrgUnidad existsOrgUnidad(String id) {
        return repositoryEntity
                .findBySigla(id)
                .orElseThrow(() -> new NoSuchElementException("Article not found : `%s`".formatted(id)));
    }

    public static OrgHierarchyInterface<OrgUnidad> generarOrgTree(List<OrgUnidad> list, OrgUnidad orgUnidad) {
        Integer idpadre = orgUnidad.getIdUnidad();

        OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
        // List<OrgUnidad> list = repositoryEntity.findAll();
        List<OrgUnidad> hijosUnid = hijos(list, idpadre);

        if (orgUnidad.getIdUnidadPadre() == null && hijosUnid.size() == 0) {
            log.info("SIN PADRE UNIDAD {}", idpadre);
            return org;
        }

        OrgUnidad root = searchRoot(list, idpadre);
        if (root == null) {
            throw new DataException("Registro padre inexistente para unidad: " + idpadre);
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

    public static OrgUnidad searchRoot(List<OrgUnidad> list, Integer idpadre) {
        if (idpadre == null) {
            return null;
        }

        Optional<OrgUnidad> oupadre = list.stream()
                .filter(r -> r.getIdUnidad() != null && r.getIdUnidad().compareTo(idpadre) == 0).findFirst();
        if (!oupadre.isPresent()) {
            throw new DataException("Registro Padre inexistente " + idpadre);
        }

        if (oupadre.get().getIdUnidadPadre() != null) {
            OrgUnidad found = searchRoot(list, oupadre.get().getIdUnidadPadre());
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
        return list.stream().filter(x -> x.getIdUnidad().compareTo(idpadre) != 0)
                .filter(r -> idpadre != null && r.getIdUnidadPadre() != null
                        && r.getIdUnidadPadre().compareTo(idpadre) == 0)
                .collect(Collectors.toList());
    }
}

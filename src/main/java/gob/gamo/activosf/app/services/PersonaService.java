package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchQueryCriteriaConsumer;
import gob.gamo.activosf.app.search.SpecSearchCriteria;
import gob.gamo.activosf.app.search.UserSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repositoryEntity;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<OrgPersona> findAll(Pageable pageable) {
        Page<OrgPersona> list = repositoryEntity.findAll(pageable).map(r -> r);
        return list;
    }

    @Transactional
    public OrgPersona crearNuevo(PersonaVO entityIn) {

        if (entityIn.idPersona() != null && entityIn.idPersona().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        OrgPersona entity = entityIn.persona();

        OrgPersona newEntity = repositoryEntity.save(entity);
        log.info("entitiy creado {}", newEntity.getIdPersona());
        return newEntity;
    }

    @Transactional
    public OrgPersona update(OrgPersona entityReq) {
        if (entityReq.getIdPersona() == null || entityReq.getIdPersona().compareTo(0) == 0)
            new DataException("Entidad con id, debe nulo");

        OrgPersona newEntity = repositoryEntity.save(entityReq);
        return newEntity;
    }

    @Transactional
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
    }

    private OrgPersona findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro not found : `%s`".formatted(id)));
    }

    public List<OrgPersona> search(final List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<OrgPersona> query = builder.createQuery(OrgPersona.class);
        final Root<OrgPersona> r = query.from(OrgPersona.class);

        Predicate predicate = builder.conjunction();
        SearchQueryCriteriaConsumer<OrgPersona> searchConsumer = new SearchQueryCriteriaConsumer<OrgPersona>(predicate,
                builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    public Page<OrgPersona> search(final String searchTxt, Pageable pageable) {
        CriteriaParser parser = new CriteriaParser();
        Deque<?> deque = parser.parse(searchTxt);
        if (deque.size() > 0) {
            GenericSpecificationsBuilder<OrgPersona> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<OrgPersona> spec = specBuilder.build(deque, UserSpecification::new);
            Page<OrgPersona> list0 = repositoryEntity.findAll(spec,pageable);
            return list0;
        }
        return Page.empty();
    }

    /*
     * public OrgPersona existsOrgPersona(String id) {
     * return repositoryEntity
     * .findBy Sigla(id)
     * .orElseThrow(() -> new
     * NoSuchElementException("Registro not found : `%s`".formatted(id)));
     * }
     */
}

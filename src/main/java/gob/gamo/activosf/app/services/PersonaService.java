package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchQueryCriteriaConsumer;
import gob.gamo.activosf.app.search.UserSpecification;
import gob.gamo.activosf.app.services.sec.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repositoryEntity;
    private final UserService userService;
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<OrgPersona> findAll(String searchTxt, Pageable pageable) {
        CriteriaParser parser = new CriteriaParser();
        Deque<?> deque = parser.parse(searchTxt);
        if (deque.size() > 0) {
            GenericSpecificationsBuilder<OrgPersona> specBuilder = new GenericSpecificationsBuilder<>();
            Specification<OrgPersona> spec = specBuilder.build(deque, UserSpecification::new);
            Page<OrgPersona> list0 = repositoryEntity.findAll(spec, pageable);
            return list0;
        }        
        Page<OrgPersona> list = repositoryEntity.findAll(pageable);
        return list;
    }
    
    public List<OrgPersona> search(SearchCriteria params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<OrgPersona> root = query.from(OrgPersona.class);
        Join<OrgEmpleado,OrgPersona> joinPersona = root.join("empleado", JoinType.INNER);
        Join<OrgPersona,User> joinUsuario = root.join("user", JoinType.INNER);        

        query.multiselect(root); // root.get("nombre")

        Predicate predicate = builder.conjunction();

        SearchQueryCriteriaConsumer<OrgPersona> searchConsumer = new SearchQueryCriteriaConsumer<OrgPersona>(
                predicate,
                builder, root);

        searchConsumer.accept(params);
        Predicate predicateR = searchConsumer.getPredicate();
        query.where(predicateR);

        List<Tuple> l = entityManager.createQuery(query).getResultList();

        return l.stream().map(r -> {
            OrgPersona e = (OrgPersona) r.get(0);
            return e;
        }).map((x) -> x).toList();
    }

    @Transactional
    public OrgPersona crearNuevo(PersonaVO entityIn) {

        if (entityIn.idPersona() != null && entityIn.idPersona().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        OrgPersona entity = entityIn.persona();

        validar(entity);

        OrgPersona newEntity = repositoryEntity.save(entity);
        updateUserFromPersona(newEntity, entityIn);

        log.info("entitiy creado {}", newEntity.getIdPersona());
        return newEntity;
    }

    @Transactional
    public OrgPersona update(PersonaVO entityIn) {
        if (entityIn.idPersona() == null || entityIn.idPersona().compareTo(0) <= 0)
            new DataException("Entidad con id invalido");

        OrgPersona entity = findById(entityIn.idPersona());
        entity = entityIn.persona();

        validar(entity);

        OrgPersona newEntity = repositoryEntity.save(entity);

        updateUserFromPersona(newEntity, entityIn);

        return newEntity;
    }

    @Transactional
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
    }

    public OrgPersona findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Persona inexistente : `%s`".formatted(id)));
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
            Page<OrgPersona> list0 = repositoryEntity.findAll(spec, pageable);
            return list0;
        }
        return Page.empty();
    }

    public static void validar(OrgPersona entity) {
        if (entity.getTipodoc() == null) {
            throw new DataException("Persona: Tipo documento requerido");
        }
        if (entity.getNumeroDocumento() == null) {
            throw new DataException("Persona: Numero documento requerido");
        }
        if (entity.getTipopers() == null) {
            throw new DataException("Persona: Tipo persona requerido");
        }

    }

    public void createUserFromPersona(OrgPersona newEntity, PersonaVO entityIn) {
        if (entityIn.user() == null) {
            return;
        }

        boolean isUser = entityIn.user() != null && !StringUtils.isBlank(entityIn.user().username())
                && !StringUtils.isBlank(entityIn.user().password());

        if (!StringUtils.isBlank(entityIn.user().username())) {
            boolean isPersona = newEntity.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
            if (!isPersona) {
                throw new DataException(
                        "Persona con codigo de usuario(" + entityIn.user().username() + ") debe ser natural");
            }

            SignUpUserRequest u = entityIn.user();
            if (u.id() != null) {
                throw new DataException("Registro de nuevo usuario con valor ID " + u.id());
            }

            if (StringUtils.isBlank(entityIn.user().password())) {
                throw new DataException("Registro de usuario: password requerido" + newEntity.getIdPersona() + " "
                        + entityIn.user().username());
            }

            SignUpUserRequest request = new SignUpUserRequest(null, newEntity.getEmail(), u.username(),
                    u.password(), newEntity.getNombre(), newEntity.getNumeroDocumento(), newEntity.getIdPersona());

            User newuser = userService.signUp(request);
            if (newuser.getIdUnidEmpl().compareTo(newEntity.getIdPersona()) != 0) {
                throw new DataException("No se pudo crear usuario " + u.username());
            }
        }
    }

    public void updateUserFromPersona(OrgPersona newEntity, PersonaVO entityIn) {
        boolean isUser = entityIn.user() != null && !StringUtils.isBlank(entityIn.user().username())
                && !StringUtils.isBlank(entityIn.user().password());
        if (entityIn.user() == null) {
            return;
        }

        Optional<User> uopt = userRepository.findByIdPersona(newEntity.getIdPersona());
        if (!uopt.isPresent()) {
            uopt = userRepository.findByUsername(entityIn.user().username());
            if (uopt.isPresent()) {
                if (uopt.get().getIdUnidEmpl() != null
                        && uopt.get().getIdUnidEmpl().compareTo(newEntity.getIdPersona()) != 0) {
                    throw new DataException(
                            "Usuario (" + entityIn.user().username() + ") registrado para otra entidad. "
                                    + uopt.get().getIdUnidEmpl());
                }
            }
        }
        log.info("en update user persona {} {} ID:: {} pr: {}", entityIn.user().username(), entityIn.user().password(),
                newEntity.getIdPersona(), uopt.isPresent());
        if (uopt.isPresent()) {
            boolean isPersona = newEntity.getTipopers().compareTo(Constants.TAB_TIPOPERS_NATURAL) == 0;
            log.info("persnent {} -> {}", newEntity.getTipopers(), isPersona);
            if (!isPersona) {
                throw new DataException(
                        "Persona con codigo de usuario(" + entityIn.user().username() + ") debe ser natural");
            }

            if (!uopt.get().getUsername().equals(entityIn.user().username())) {
                throw new DataException("Persona con usuario " + entityIn.user().username()
                        + " difiere con el registrado " + uopt.get().getUsername());
            }

            UpdateUserRequest updu = new UpdateUserRequest(newEntity.getEmail(), entityIn.user().username(),
                    entityIn.user().password(),
                    newEntity.getNombre(), newEntity.getNumeroDocumento(), newEntity.getIdPersona(), new ArrayList<>());
            userService.updateUser(updu);
        } else {
            createUserFromPersona(newEntity, entityIn);
        }
    }

}

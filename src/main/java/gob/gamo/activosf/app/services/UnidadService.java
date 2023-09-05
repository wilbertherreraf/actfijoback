package gob.gamo.activosf.app.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadService {
    private final OrgUnidadRepository repositoryEntity;

    @Transactional(readOnly = true)
    public Page<UnidadResponse> findAll(Pageable pageable) {
        Page<UnidadResponse> list = repositoryEntity.findAll(pageable).map(r -> new UnidadResponse(r));
        return list;
    }

    @Transactional
    public UnidadResponse crearNuevo(OrgUnidad entity) {
        log.info("ingresando a nuevo {}", entity.toString());
        if (entity.getIdUnidad() != null && entity.getIdUnidad().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        OrgUnidad newEntity = repositoryEntity.save(entity);
        log.info("entitiy creado {}", newEntity.getIdUnidad());
        return new UnidadResponse(newEntity);
    }

    @Transactional
    public UnidadResponse update(UnidadResponse entityReq) {
        log.info("ingresando a update {}", entityReq.toString());

        if (entityReq.id() == null || entityReq.id().compareTo(0) == 0) new DataException("Entidad con id, debe nulo");

        OrgUnidad entity = OrgUnidad.createOrgUnidad(entityReq);

        OrgUnidad newEntity = repositoryEntity.save(entity);
        log.info("entitiy update {}", newEntity.getIdUnidad());
        return new UnidadResponse(newEntity);
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

    /*     @Override
    public Page<OrgUnidad> getAllocatedList(OrgEmpleado OrgEmpleado, OrgUnidad OrgUnidad, Pageable pageable) {
        return this.repositoryEntity.findAll(new Specification<OrgUnidad>() {
            @Override
            public Predicate toPredicate(Root<OrgUnidad> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("idUnidad"), OrgEmpleado.getId()));
                if (!StringUtils.isEmpty(OrgUnidad.getUserName())) {
                    predicates.add(criteriaBuilder.equal(root.get("userName"), OrgUnidad.getUserName()));
                }
                if (!StringUtils.isEmpty(OrgUnidad.getCellphone())) {
                    predicates.add(criteriaBuilder.equal(root.get("cellphone"), OrgUnidad.getCellphone()));
                }
                if (!StringUtils.isEmpty(OrgUnidad.getEmail())) {
                    predicates.add(criteriaBuilder.equal(root.get("email"), OrgUnidad.getEmail()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    } */
}

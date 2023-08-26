package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        if (entityReq.id() == null || entityReq.id().compareTo(0) == 0)
            new DataException("Entidad con id, debe nulo");

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
}

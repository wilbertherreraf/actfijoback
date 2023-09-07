package gob.gamo.activosf.app.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaService {
private final PersonaRepository repositoryEntity;

    @Transactional(readOnly = true)
    public Page<OrgPersona> findAll(Pageable pageable) {
        Page<OrgPersona> list = repositoryEntity.findAll(pageable);
        return list;
    }

    @Transactional
    public OrgPersona crearNuevo(OrgPersona entity) {
        if (entity.getIdPersona() != null && entity.getIdPersona().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        OrgPersona newEntity = repositoryEntity.save(entity);
        log.info("entitiy creado {}", newEntity.getIdPersona());
        return newEntity;
    }

    @Transactional
    public OrgPersona update(OrgPersona entityReq) {
        if (entityReq.getIdPersona() == null || entityReq.getIdPersona().compareTo(0) == 0) new DataException("Entidad con id, debe nulo");

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

    /* public OrgPersona existsOrgPersona(String id) {
        return repositoryEntity
                .findBy Sigla(id)
                .orElseThrow(() -> new NoSuchElementException("Registro not found : `%s`".formatted(id)));
    }     */    
}

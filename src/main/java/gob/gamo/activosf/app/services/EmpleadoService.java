package gob.gamo.activosf.app.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository repositoryEntity;

    @Transactional(readOnly = true)
    public Page<OrgEmpleado> findAll(Pageable pageable) {
        Page<OrgEmpleado> list = repositoryEntity.findAll(pageable);
        return list;
    }

    @Transactional
    public OrgEmpleado crearNuevo(OrgEmpleado entity) {
        if (entity.getId() != null && entity.getId().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        OrgEmpleado newEntity = repositoryEntity.save(entity);
        log.info("entitiy creado {}", newEntity.getId());
        return newEntity;
    }

    @Transactional
    public OrgEmpleado update(OrgEmpleado entityReq) {
        if (entityReq.getId() == null || entityReq.getId().compareTo(0) == 0) new DataException("Entidad con id, debe nulo");

        OrgEmpleado newEntity = repositoryEntity.save(entityReq);
        return newEntity;
    }

    @Transactional
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
    }    
        
    private OrgEmpleado findById(Integer id) {
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro not found : `%s`".formatted(id)));
    }

    /* public OrgEmpleado existsOrgEmpleado(String id) {
        return repositoryEntity
                .findBy Sigla(id)
                .orElseThrow(() -> new NoSuchElementException("Registro not found : `%s`".formatted(id)));
    }     */
}

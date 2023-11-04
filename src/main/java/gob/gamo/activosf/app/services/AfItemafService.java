package gob.gamo.activosf.app.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfItemafRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AfItemafService {
    private final AfItemafRepository repositoryEntity;


    @Transactional(readOnly = true)
    public Page<AfItemaf> findAll(Pageable pageable) {
        Page<AfItemaf> list = repositoryEntity.findAll(pageable);
        return list;
    }

    @Transactional
    public AfItemaf crearNuevo(AfItemaf item) {
        AfItemaf nitem  = repositoryEntity.save(item);
        return nitem;
    }

    @Transactional
    public AfItemaf update(AfItemaf item) {
        AfItemaf nitem  = repositoryEntity.save(item);
        return nitem;
    }    

    public AfItemaf findById(Integer id) {
        if (id == null) {
            throw new DataException("Codigo de item nulo");
        }
        
        return repositoryEntity
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item inexistente : `%s`".formatted(id)));
    }    
}

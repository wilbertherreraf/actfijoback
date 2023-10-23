package gob.gamo.activosf.app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.dto.UnidadResponse;
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
}

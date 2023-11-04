package gob.gamo.activosf.app.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenDesctablaService {
    private final GenDesctablaRespository repository;

    @Transactional(readOnly = true)
    public GenDesctabla find(Integer tab, Integer cod) {
        if (tab == null || cod  == null){
            throw new DataException("Error al determinar catalogo, valores nulos.");
        }
        return repository.findByDesCodtabAndDesCodigo(tab, cod)
                .orElseThrow(() -> new DataException("Catalogo inexistente " + tab + " " + cod));
    }
}

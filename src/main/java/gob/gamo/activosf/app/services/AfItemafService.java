package gob.gamo.activosf.app.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.dto.ItemafVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfItemafRepository;

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
        AfItemaf nitem = repositoryEntity.save(item);
        return nitem;
    }

    @Transactional
    public AfItemaf update(AfItemaf item) {

        AfItemaf nitem = repositoryEntity.save(item);
        return nitem;
    }

    @Transactional
    public AfItemaf update(ItemafVo t) {
        AfItemaf old = findById(t.id());

        old.setCodnemo(t.codnemo());
        old.setNivel(t.nivel());
        old.setTipo(t.tipo());
        old.setGrupo(t.grupo());
        old.setClase(t.clase());
        old.setFamilia(t.familia());
        old.setItem(t.item());
        old.setNombre(t.nombre());
        old.setUnidmedida(t.unidmedida());
        old.setCodclasif(t.codclasif());
        old.setTipoCostodi(t.tipoCostodi());
        old.setTipoCostofv(t.tipoCostofv());
        old.setPunit(t.punit());
        old.setStock(t.stock());
        old.setStockMin(t.stockMin());
        old.setTabUmedida(t.tabUmedida());
        old.setUmedida(t.umedida());

        repositoryEntity.save(old);
        return old;
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

package gob.gamo.activosf.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.repository.TxTransdetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransdetService {
    private final TxTransdetRepository txTransdetRepository;

    @Transactional(readOnly = true)
    public List<TxTransdet> getAll(TxTransaccion trx) {
        return txTransdetRepository.findByIdTransaccion(trx.getIdTransaccion());
    }

    @Transactional
    public TxTransdet create(TxTransaccion trx, TxTransdet trxdet) {
        trxdet.setFechaOper(trx.getFechaOper());
        trxdet.setIdTransaccion(trx.getIdTransaccion());
        TxTransdet newTrx = txTransdetRepository.save(trxdet);
        return newTrx;
    }
}

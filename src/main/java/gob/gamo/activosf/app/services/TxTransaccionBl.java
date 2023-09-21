package gob.gamo.activosf.app.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;

/**
 * Session Bean implementation class TxTransaccionBl
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransaccionBl {

    TxTransaccionRepository txTransaccionRepository;

    public TxTransaccion generateTxTransaccion(UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = new TxTransaccion();
        txTransaccion.setTxFecha(userRequestVo.getDate());
        txTransaccion.setTxHost(userRequestVo.getHost());
        txTransaccion.setTxUsuario(userRequestVo.getUserId().intValue());
        txTransaccionRepository.save(txTransaccion);
        return txTransaccion;
    }
}

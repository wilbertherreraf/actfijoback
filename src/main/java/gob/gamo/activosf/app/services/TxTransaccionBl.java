package gob.gamo.activosf.app.services;





import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;



/**
 * Session Bean implementation class TxTransaccionBl
 */


public class TxTransaccionBl {

	
	
	TxTransaccionRepository txTransaccionRepository;
	
    public TxTransaccionBl() {
    }
    
    public TxTransaccion generateTxTransaccion(UserRequestVo userRequestVo){
    	TxTransaccion txTransaccion = new TxTransaccion();
    	txTransaccion.setTxFecha(userRequestVo.getDate());
    	txTransaccion.setTxHost(userRequestVo.getHost());
    	txTransaccion.setTxUsuario(userRequestVo.getUserId().intValue());
    	txTransaccionRepository.save(txTransaccion);
    	return txTransaccion;
    }

}

package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.config.JwtTokenProvider;
import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.repository.TxTransdetRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.services.sec.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest
public class TransaccionTest {
    @Autowired
    private UserService userService;
    @Autowired
    PersonaService personaService;
    @Autowired
    AfSearchService AfSearchService;
    @Autowired
    TxAlmService almService;
    @Autowired
    AfItemafService itemafService;

    @Autowired
    TxTransaccionService txTransaccionService;
    @Autowired
    TxTransdetService txTransdetService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider bearerTokenSupplier;
    @Autowired
    TxTransdetRepository transdetRepository;
    @Autowired
    TxTransaccionRepository transaccionRepository;

    @Test
    public void createKdxTransac() {
        try {
            User me = getUser();
            TxTransaccion trx = createTransacData();
            prinlog(trx.getIdTransaccion());
            TxTransdet trxdet = trx.getTransdet().get(0);
            //almService.retCreateKdxTrxDet(me, trx, trxdet);
            /* TxTransaccion txkdx = almService.retCreateKdxTrx(me, trx, trxdet);
            prinlog(txkdx.getIdTransaccion()); */
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void createInputTransac() {
        try {
            User me = getUser();
            TxTransaccion trx = createTransacData();
            prinlog(trx.getIdTransaccion());
            TxTransdet trxdet = trx.getTransdet().get(0);
            almService.processInputStock(me, trx, trxdet);
            /* TxTransaccion txkdx = almService.retCreateKdxTrx(me, trx, trxdet);
            prinlog(txkdx.getIdTransaccion()); */
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void createInputListTransac() {
        try {
            User me = getUser();
            TxTransaccion trx = createTransacData();
            prinlog(trx.getIdTransaccion());
            almService.modAlmProcess(me, trx);
            List<TxTransdet> list = transdetRepository.findByIdTransaccion(trx.getIdTransaccion());
            for (TxTransdet td : list) {
                log.info("-------- {} [{}]--------", td.getIdTransaccion(), td.getIdItemaf());
                List<TxTransaccion> tl = transaccionRepository.findByItemaf(trx.getTipoopersub(),
                        Constants.TAB_TOP_KARDEX, td.getIdItemaf());
                if (tl.size() > 0) {
                    prinlog(tl.get(0).getIdTransaccion());
                }
            }

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

     @Test
    public void createOutListTransac() {
        try {
            createInputListTransac();
            User me = getUser();
            TxTransaccion trx = createTransacDataOut();
            prinlog(trx.getIdTransaccion());
            almService.modAlmProcess(me, trx);
            List<TxTransdet> list = transdetRepository.findByIdTransaccion(trx.getIdTransaccion());
            for (TxTransdet td : list) {
                log.info("-------- {} [{}]--------", td.getIdTransaccion(), td.getIdItemaf());
                List<TxTransaccion> tl = transaccionRepository.findByItemaf(trx.getTipoopersub(),
                        Constants.TAB_TOP_KARDEX, td.getIdItemaf());
                if (tl.size() > 0) {
                    prinlog(tl.get(0).getIdTransaccion());
                }
            }

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
    public void prinlog(Integer idtrx) {
        TxTransaccion trx = txTransaccionService.findByIdAndComplete(idtrx);
        log.info("ID {} trxpadre {} tOper {} tarea {} ", trx.getIdTransaccion(), trx.getIdTrxorigen(),
                trx.getTipooperacion(),
                trx.getTipooperaciondesc().getDesDescrip());
        List<TxTransdet> list = transdetRepository.findByIdTransaccion(idtrx);
        for (TxTransdet trxdet : list) {
            txTransdetService.completeRels(trxdet);
            log.info("  ID {} padre: {} -> [{}] toper {} my: {} amt{} amorig {} amtcont {}", trxdet.getIdTransdet(),
                    trxdet.getIdTransdetpadre(), trxdet.getIdItemaf(),
                    trxdet.getTipooperaciondesc().getDesDescrip(),
                    trxdet.getOpermayordesc().getDesDescrip(), trxdet.getMonto(), trxdet.getMontoOrig(),
                    trxdet.getMontoCont());
        }
    }

    public TxTransaccion createTransacData() {

        User u = getUser();
        TxTransaccion tri = crearDataTrx(Constants.TAB_ALM_INGRESO);
        TxTransaccion trxini = txTransaccionService.prepareOperation(u, tri);

        // tri.setIdEmpleado(u.getIdUnidEmpl());
        TxTransaccion trx = txTransaccionService.registro(u, trxini);
        TxTransdet trxd = crearDataTrxdet(4899,BigDecimal.valueOf(100), BigDecimal.valueOf(100.3));
        TxTransdet result = txTransdetService.updInfoItemaf(u, trx, trxd);
        txTransdetService.create(u, trx, result);

        trxd = crearDataTrxdet(3372,BigDecimal.valueOf(500), BigDecimal.valueOf(1000.3));
        result = txTransdetService.updInfoItemaf(u, trx, trxd);
        TxTransdet trxdet = txTransdetService.create(u, trx, result);
        // trx.getTransdet().add(trxdet);
        return trx;

    }

    public TxTransaccion createTransacDataOut() {
        User u = getUser();
        TxTransaccion tri = crearDataTrx(Constants.TAB_ALM_SALIDA);
        TxTransaccion trxini = txTransaccionService.prepareOperation(u, tri);

        // tri.setIdEmpleado(u.getIdUnidEmpl());
        TxTransaccion trx = txTransaccionService.registro(u, trxini);
        TxTransdet trxd = crearDataTrxdet(4899,BigDecimal.valueOf(1), BigDecimal.valueOf(1.3));
        TxTransdet result = txTransdetService.updInfoItemaf(u, trx, trxd);
        txTransdetService.create(u, trx, result);

        trxd = crearDataTrxdet(3372, BigDecimal.valueOf(2), BigDecimal.valueOf(1.36));
        result = txTransdetService.updInfoItemaf(u, trx, trxd);
        TxTransdet trxdet = txTransdetService.create(u, trx, result);
        // trx.getTransdet().add(trxdet);
        return trx;

    }
    public TxTransaccion crearDataTrx(Integer idOper) {
        TxTransaccion tx = TxTransaccion.nuevoReg();
        tx.setTipoopersub(Constants.TAB_MD_AL);
        tx.setTipooperacion(idOper);

        return tx;
    }

    public TxTransdet crearDataTrxdet(Integer idItemaf, BigDecimal c, BigDecimal mo  ) {
        TxTransdet trxdet = TxTransdet.nuevoReg();
        trxdet.setMonto(BigDecimal.valueOf(12.3));
        trxdet.setCantidad(c);
        trxdet.setMontoOrig(mo);
        trxdet.setMontoDesc(BigDecimal.valueOf(10.3));

        AfItemaf itemid = itemafService.findById(idItemaf);
        trxdet.setIdItemaf(itemid.getId());

        return trxdet;
    }

    public User getUser() {
        LoginUserRequest request = new LoginUserRequest("asdf", "asdf");
        return login(request);
    }

    public User login(LoginUserRequest request) {
        return userRepository
                .findByUsername(request.username())
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    String token = bearerTokenSupplier.supply(user);
                    return user.possessToken(token);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

}

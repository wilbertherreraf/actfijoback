package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.repository.AfItemafRepository;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.repository.TxTransdetRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransdetQryService {
    private final TxTransdetRepository transdetRepository;
    private final AfItemafService itemafService;
    private final AfItemafRepository itemRepositoryEntity;
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoService empleadoService;
    private final OrgUnidadRepository unidadRepository;
    private final GenDesctablaRespository tablasRespository;
    private final GenDesctablaService tablasService;

    @Transactional(readOnly = true)
    public TxTransdet summarizeTrx(TxTransaccion trx) {
        TxTransdet trxdet = TxTransdet.nuevoReg();
        List<TxTransdet> list = transdetRepository.findByIdTransaccion(trx.getIdTransaccion());
        trxdet.setMonto(totalMonto(trx, list));
        trxdet.setMontoDesc(totalMontoDesc(trx, list));
        trxdet.setMontoOrig(totalMontoOrig(trx, list));
        trxdet.setMontoCont(totalMontoCont(trx, list));
        trxdet.setTipoCambio(totalTipocambio(trx, list));
        trxdet.setGlosa((new Date()).toString());
        trxdet.setIdCorrelativo(list.size());
        return trxdet;
    }

    public static TxTransdet summarizeList(TxTransaccion trx, List<TxTransdet> list) {
        TxTransdet trxdet = TxTransdet.nuevoReg();

        trxdet.setTipoCambio(totalTipocambio(trx, list));
        trxdet.setMontoDesc(totalMontoDesc(trx, list));
        trxdet.setMontoOrig(totalMontoOrig(trx, list));
        trxdet.setMonto(totalMonto(trx, list));
        trxdet.setMontoCont(totalMontoCont(trx, list));

        return trxdet;
    }

    public static BigDecimal totalMonto(TxTransaccion trx, List<TxTransdet> list) {
        return list.stream()
                .map(x -> totalMontoXCantidad(trx, x))
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal totalMontoCont(TxTransaccion trx, List<TxTransdet> list) {
        return list.stream()
                .map(x -> totalContMontoXCantidad(trx, x))
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal totalMontoDesc(TxTransaccion trx, List<TxTransdet> list) {
        return list.stream()
                .map(x -> x.getMontoDesc() != null ? x.getMontoDesc() : BigDecimal.ZERO)
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal totalMontoOrig(TxTransaccion trx, List<TxTransdet> list) {
        return list.stream()
                .map(x -> x.getMontoOrig() != null ? x.getMontoOrig() : BigDecimal.ZERO)
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal totalTipocambio(TxTransaccion trx, List<TxTransdet> list) {
        BigDecimal value = BigDecimal.ZERO;
        value = list.stream()
                .map(x -> x.getTipoCambio() != null ? x.getTipoCambio() : BigDecimal.ZERO)
                .reduce((a, b) -> a.add(b))
                .orElse(BigDecimal.ZERO);
        return value;
    }

    public static BigDecimal totalMontoXCantidad(TxTransaccion trx, TxTransdet trxdet) {
        BigDecimal taxRate = new BigDecimal(0.13);
        BigDecimal value =
                calculateTotalAmount(trxdet.getCantidad(), trxdet.getMontoOrig(), trxdet.getMontoDesc(), taxRate);
        return value;
    }

    public static BigDecimal totalContMontoXCantidad(TxTransaccion trx, TxTransdet trxdet) {
        BigDecimal taxRate = new BigDecimal(0.00);
        BigDecimal discount = new BigDecimal(0.00);
        ;
        BigDecimal value = calculateTotalAmount(trxdet.getCantidad(), trxdet.getMontoOrig(), discount, taxRate);
        return value;
    }

    public static BigDecimal totalDescMontoXCantidad(TxTransaccion trx, TxTransdet trxdet) {
        BigDecimal taxRate = new BigDecimal(0.00);
        BigDecimal discount = new BigDecimal(0.00);
        ;
        BigDecimal value = calculateTotalAmount(trxdet.getCantidad(), trxdet.getMontoOrig(), discount, taxRate);
        return value;
    }

    public static BigDecimal calculateTotalAmount(
            BigDecimal quantity, BigDecimal unitPrice, BigDecimal discountRate, BigDecimal taxRate) {
        quantity = quantity == null ? BigDecimal.ZERO : quantity;
        unitPrice = unitPrice == null ? BigDecimal.ZERO : unitPrice;
        discountRate = discountRate == null ? BigDecimal.ZERO : discountRate;
        BigDecimal amount = quantity.multiply(unitPrice);
        BigDecimal discount = amount.multiply(discountRate);
        BigDecimal discountedAmount = amount.subtract(discount);
        BigDecimal tax = discountedAmount.multiply(taxRate);
        BigDecimal total = discountedAmount.add(tax);

        // round to 2 decimal places using HALF_EVEN
        BigDecimal roundedTotal = total.setScale(2, RoundingMode.HALF_EVEN);

        return roundedTotal;
    }

    BigDecimal generateOpenPrice() {
        float min = 70;
        float max = 120;
        return BigDecimal.valueOf(min + new Random().nextFloat() * (max - min)).setScale(4, RoundingMode.CEILING);
    }
}

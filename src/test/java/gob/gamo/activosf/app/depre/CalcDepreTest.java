package gob.gamo.activosf.app.depre;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import gob.gamo.activosf.app.depre.model.DepreciationPeriod;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.utils.UtilsDate;
import lombok.extern.slf4j.Slf4j;

import org.javamoney.moneta.Money;

@Slf4j
public class CalcDepreTest {
    // private final static Currency KES = Currency.getInstance("KES");
    private final static FixedAsset radio = new FixedAsset("Radio", Money.of(200, "KES"), "Electronics", "001",
            LocalDate.of(2018, 2, 5), "abc01", Money.of(9.5, "KES"));
    private final static FixedAsset lenovo = new FixedAsset("Lenovo IDP110", Money.of(5600, "KES"), "COMPUTERS", "987",
            LocalDate.of(2018, 2, 13), "abc02", Money.of(13.42, "KES"));
    private final static FixedAsset chair = new FixedAsset("Chair", Money.of(156, "KES"), "FURNITURE", "010",
            LocalDate.of(2018, 1, 13), "abc03", Money.of(19.24, "KES"));

    private final DepreciationPeriod period = Mockito.mock(DepreciationPeriod.class);
    // List<AccountingEntry> entries;
    //private DefaultDepreciationEntryResolver batchEntryResolver;
    private List<FixedAsset> fixedAssets = ImmutableList.of(radio, lenovo, chair);

    @Test
    public void calDepre() {
        try {

            // give
            log.info("------------- inicio -------------");
            // PurchasePrice=1000&Life=5&Section179=200&AssetName=MacrsHyAsset&PurchaseDate=10%2F01%2F2020
            double fPurchasePrice = 1000;
            double fResidualValue = 0;
            int iUSefulLife = 10;
            String sName = "";
            double fSection179 = 200;
            Date purchaseDate = UtilsDate.dateFromString("01/11/2013", "dd/MM/yyyy");
            log.info("Q::: {} {}", UtilsDate.getCuatrimestre(purchaseDate), UtilsDate.getCuatrimestre(purchaseDate));
            Asset asset = new Asset(fPurchasePrice, fResidualValue, iUSefulLife, sName, fSection179);
            asset.calcSL();
            List<DepYear> a = asset.GaapDepreciation;
            a.forEach(x -> {
                log.info("depre year {} , acum: {} exp {}", x.Year, x.AccumulatedDepreciation, x.Expense);
            });
            log.info("------------- calcDB200 -------------");
            asset.calcDB200();
            a = asset.GaapDepreciation;
            a.forEach(x -> {
                log.info("depre year {} , acum: {} exp {}", x.Year, x.AccumulatedDepreciation, x.Expense);
            });

            log.info("------------- calcSYD -------------");
            asset.calcSYD();
            a = asset.GaapDepreciation;
            a.forEach(x -> {
                log.info("depre year {} , acum: {} exp {}", x.Year, x.AccumulatedDepreciation, x.Expense);
            });

            log.info("------------- calcMacrsHY -------------");
            asset = new Asset("AssetName", purchaseDate, fPurchasePrice, 0.0, iUSefulLife, iUSefulLife, fSection179,
                    "");
            /*
             * (String sName, Date dtPurchaseDate, double fPurchasePrice, double
             * fResidualValue, int iUSefulLife,
             * int iTaxLife, double fSection179, String sDescription)
             */
            asset.calcMacrsHY();
            a = asset.TaxDepreciation;
            a.forEach(x -> {
                log.info("depre year {} , acum: {} exp {}", x.Year, x.AccumulatedDepreciation, x.Expense);
            });

            log.info("------------- calcMacrsMQ -------------");
            asset.calcMacrsMQ();
            a = asset.TaxDepreciation;
            a.forEach(x -> {
                log.info("depre year {} , acum: {} exp {}", x.Year, x.AccumulatedDepreciation, x.Expense);
            });
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }

    @Test
    public void calDepreciationBatch() {
        try {
            //DepreciationAlgorithm depreciationAlgorithm = Mockito.mock(DepreciationAlgorithm.class);
            //when(depreciationAlgorithm.name()).thenReturn("Mock Depreciation Algorithm");

//            batchEntryResolver = new DefaultDepreciationEntryResolver(accountResolver, depreciationAlgorithm);

//            entries = batchEntryResolver.resolveEntries(fixedAssets, period);
            // give
            log.info("------------- inicio -------------");

        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }
}

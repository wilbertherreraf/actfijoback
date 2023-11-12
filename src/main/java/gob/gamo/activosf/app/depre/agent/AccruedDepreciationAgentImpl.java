package gob.gamo.activosf.app.depre.agent;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.exception.DepreciationExecutionException;
import gob.gamo.activosf.app.depre.model.AccruedDepreciation;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;

/**
 * This object calculates the AccruedDepreciation given the Asset, the Month in
 * which the Accrual is effective and the DepreciationProceeds where we store
 * the depreciation values
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@Component("accruedDepreciationAgent")
public class AccruedDepreciationAgentImpl implements AccruedDepreciationAgent {
    /**
     * {@inheritDoc}
     */
    @Override
    public AccruedDepreciation invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds) {

        log.trace("Processing accruedDepreciation for asset: {} in the period : {}", asset, month);

        // with fingers crossed : Hope by the time you are here, the fixedAsser
        // netBookValue will have changed
        Money depreciationAcc = asset.getPurchaseCost().subtract(asset.getNetBookValue());
        log.trace("Reporting accruedDepreciation as : {}", depreciationAcc);
        AccruedDepreciation accruedDepreciation = createAccruedDepreciation(asset, month, depreciationAcc);
        log.trace("Sending AccruedDepreciation item created : {}", accruedDepreciation);
        // send(()-> accruedDepreciation);
        proceeds.setAccruedDepreciation(accruedDepreciation);

        return accruedDepreciation;
    }

    /**
     * Creates {@link AccruedDepreciation} instance relative to the parameter items
     * and fixedAsset item given
     *
     * @param asset   FixedAsset item whose accruedDepreciation we are to derive
     * @param month   YearMonth in which the accruedDepreciation is relevant
     * @param accrual The actual amount of depreciation as double-precision
     * @return AccruedDepreciation item to be returned to the caller for further
     *         processing and persistence
     */
    private AccruedDepreciation createAccruedDepreciation(FixedAsset asset, YearMonth month, Money accrual) {
        AccruedDepreciation accruedDepreciation = new AccruedDepreciation();

        log.trace("Creating accruedDepreciation instance relative to the asset : {}, for the month : {}", asset, month);

        try {
            // double accrual =
            // accruedDepreciationService.getAccruedDepreciationForAsset(asset,month) +
            // depreciationAmount;
            accruedDepreciation.setMonth(month)
                    .setCategory(asset.getCategory())
                    .setFixedAssetId(asset.getId())
                    .setCategory(asset.getCategory())
                    .setSolId(asset.getSolId())
                    .setAccruedDepreciation(accrual);
        } catch (Throwable e) {
            String message = String.format("Exception encountered while creating accruedDepreciation instance relative"
                    + " to the asset : %s for the month : %s", asset, month);
            throw new DepreciationExecutionException(message, e);
        }

        log.trace("AccruedDepreciation instance created : {}", accruedDepreciation);

        return accruedDepreciation;
    }

}

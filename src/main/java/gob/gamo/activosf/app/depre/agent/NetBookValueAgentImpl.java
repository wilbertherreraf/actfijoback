package gob.gamo.activosf.app.depre.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.exception.DepreciationExecutionException;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.model.NetBookValue;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;

/**
 * On invocation calculates the NetBookValue for any given Asset, at a specified
 * Month and records the same into the DepreciationProceeds item provided
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@Component("netBookValueAgent")
public class NetBookValueAgentImpl implements NetBookValueAgent {
    /**
     * {@inheritDoc}
     * <p>
     * Upon invocation the implementation will return the netBoookValue item for the
     * relevant month in which depreciation has occured
     */
    @Override
    public NetBookValue invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds) {
        log.trace("Processing NetBookValue item for the asset : {} in the period : {}", asset, month);
        NetBookValue netBookValue = createNetBookValue(asset, month);
        log.trace("Sending netBookValueItem created : {}", netBookValue);
        // send(()->netBookValue);

        proceeds.setNetBookValue(netBookValue);
        return netBookValue;
    }

    /**
     * Creates a NetBookValue object using the parameters given
     *
     * @param asset the FixedAsset item whose netBookValue is to be revalued
     * @param month of the netBookValue valuation
     * @return NetBookValue as at the month
     */
    private NetBookValue createNetBookValue(FixedAsset asset, YearMonth month) {
        NetBookValue netBookValue = new NetBookValue();

        log.trace("Creating netBookValue instance relative to the asset : {} for the month : {}", asset, month);
        try {
            netBookValue.setCategory(asset.getCategory()).setFixedAssetId(asset.getId()).setMonth(month)
                    .setSolId(asset.getSolId()).setNetBookValue(asset.getNetBookValue());
        } catch (Throwable e) {
            String message = String.format("Exception encountered while creating netBookValue instance relative"
                    + "to the asset : %s for the month : %s", asset, month);
            throw new DepreciationExecutionException(message, e);
        }

        log.trace("NetBookValue item created : {}", netBookValue);

        return netBookValue;
    }

}

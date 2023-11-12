package gob.gamo.activosf.app.depre.agent;

import java.time.YearMonth;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.model.NetBookValue;

/**
 * Another Agent in the depreciation chain which calculates and generates the
 * appropriate NetBookValue for a FixedAsset at a given time period in months
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface NetBookValueAgent extends Agent<NetBookValue> {
    /**
     * {@inheritDoc}
     * <p>
     * Upon invocation the implementation will return the netBoookValue item for the
     * relevant month in which depreciation has occured
     */
    NetBookValue invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds);
}

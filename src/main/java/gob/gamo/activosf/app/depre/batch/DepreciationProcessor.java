package gob.gamo.activosf.app.depre.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gob.gamo.activosf.app.depre.DepreciationExecutor;
import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.util.ProcessingList;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemProcessor;

/**
 * Calculates the depreciation in a given month for a given Asset. A list is
 * generated and sent to the writer for persistence
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
public class DepreciationProcessor implements ItemProcessor<FixedAsset, ProcessingList<DepreciationProceeds>> {
    private DepreciationRelay depreciationRelay;
    private DepreciationExecutor depreciationExecutor;
    private ProcessingList<DepreciationProceeds> processingList;

    /**
     * <p>
     * Constructor for DepreciationProcessor.
     * </p>
     *
     * @param processingList a
     *                       {@link io.github.fasset.fasset.kernel.util.ProcessingList}
     *                       object.
     */
    public DepreciationProcessor(ProcessingList<DepreciationProceeds> processingList) {
        this.processingList = processingList;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Process the provided item, returning a potentially modified or new item for
     * continued processing. If the returned result is null, it is assumed that
     * processing of the item should not
     * continue.
     */
    @Override
    public ProcessingList<DepreciationProceeds> process(FixedAsset fixedAsset) throws Exception {
        // ProcessingList<DepreciationProceeds> depreciationProceeds = new
        // ProcessingListImpl<>();
        depreciationRelay.getMonthlyDepreciationSequence().forEach(i -> {
            log.trace("Calculating depreciation in the month of :{} for asset {}", i, fixedAsset);
            processingList.add(depreciationExecutor.getDepreciation(fixedAsset, i));
        });

        return processingList;
    }

    /**
     * <p>
     * Setter for the field <code>depreciationRelay</code>.
     * </p>
     *
     * @param depreciationRelay a
     *                          {@link io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationRelay}
     *                          object.
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationProcessor}
     *         object.
     */
    @Autowired
    @Qualifier("depreciationRelay")
    public DepreciationProcessor setDepreciationRelay(DepreciationRelay depreciationRelay) {
        this.depreciationRelay = depreciationRelay;
        return this;
    }

    /**
     * <p>
     * Setter for the field <code>depreciationExecutor</code>.
     * </p>
     *
     * @param depreciationExecutor a
     *                             {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationExecutor}
     *                             object.
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationProcessor}
     *         object.
     */
    @Autowired
    @Qualifier("depreciationExecutor")
    public DepreciationProcessor setDepreciationExecutor(DepreciationExecutor depreciationExecutor) {
        this.depreciationExecutor = depreciationExecutor;
        return this;
    }
}

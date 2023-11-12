/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package gob.gamo.activosf.app.depre;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.depre.convert.LocalDateToYearMonthConverter;
import gob.gamo.activosf.app.depre.model.Depreciation;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.model.NilDepreciation;

import java.time.YearMonth;

/**
 * This class represents the main method which is to be abstracted by other
 * layers that would allow for flexibility in application of business rules, the
 * main method is the {@link
 * DepreciationExecutorImpl#getDepreciation(FixedAsset, YearMonth)} which able
 * to extract a {@link io.github.fasset.fasset.model.Depreciation} as long as
 * you have a {@link
 * io.github.fasset.fasset.model.FixedAsset} and the {@link java.time.YearMonth}
 * month for which the depreciation is to be calculated
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Service("depreciationExecutor")
@Transactional
public class DepreciationExecutorImpl implements DepreciationExecutor {

    private static final Logger log = LoggerFactory.getLogger(DepreciationExecutorImpl.class);

    private LocalDateToYearMonthConverter localDateToYearMonthConverter;
    private DepreciationAgentsHandler depreciationAgentsHandler;
    private Depreciation depreciation;
    private DepreciationProceeds depreciationProceeds;
    private String moneyProperties;

    /**
     * <p>
     * Setter for the field <code>moneyProperties</code>.
     * </p>
     *
     * @param moneyProperties a
     *                        {@link io.github.fasset.fasset.config.MoneyProperties}
     *                        object.
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationExecutorImpl}
     *         object.
     */
    @Autowired
    public DepreciationExecutorImpl setMoneyProperties(String moneyProperties) {
        this.moneyProperties = moneyProperties;
        return this;
    }

    /**
     * <p>
     * Setter for the field <code>localDateToYearMonthConverter</code>.
     * </p>
     *
     * @param localDateToYearMonthConverter a
     *                                      {@link io.github.fasset.fasset.kernel.util.convert.LocalDateToYearMonthConverter}
     *                                      object.
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationExecutorImpl}
     *         object.
     */
    @Autowired
    public DepreciationExecutorImpl setLocalDateToYearMonthConverter(
            LocalDateToYearMonthConverter localDateToYearMonthConverter) {
        this.localDateToYearMonthConverter = localDateToYearMonthConverter;
        return this;
    }

    /**
     * <p>
     * Setter for the field <code>depreciationAgentsHandler</code>.
     * </p>
     *
     * @param depreciationAgentsHandler a
     *                                  {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationAgentsHandler}
     *                                  object.
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationExecutorImpl}
     *         object.
     */
    @Autowired
    public DepreciationExecutorImpl setDepreciationAgentsHandler(DepreciationAgentsHandler depreciationAgentsHandler) {
        this.depreciationAgentsHandler = depreciationAgentsHandler;
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns a Depreciation object given the fixed asset, and updates the fixed
     * asset with the new net book value and the month of depreciation
     */
    @Cacheable("depreciationProceeds")
    @Override
    public DepreciationProceeds getDepreciation(FixedAsset asset, YearMonth month) {
        DepreciationProceeds depreciationProceeds = new DepreciationProceeds();

        checkIfNull(asset, month);

        if (asset.getNetBookValue().isEqualTo(Money.of(0.00, moneyProperties))) {
            log.trace("The netBookValue for asset : {} is nil, skipping depreciation and resorting to nil "
                    + "depreciation", asset);

            depreciation = getNilDepreciation(asset, month);

            depreciationProceeds.setDepreciation(depreciation)
                    .setNetBookValue(new UnModifiedNetBookValue(asset, month).getNetBookValue())
                    .setAccruedDepreciation(new UnModifiedAccruedDepreciation(asset, month).getAccruedDepreciation());

        } else if (localDateToYearMonthConverter.convert(asset.getPurchaseDate()).isAfter(month)) {
            log.trace("The depreciation period : {} is sooner that the assets purchase date {} "
                    + "resorting to nil depreciation", month, asset.getPurchaseDate());

            depreciation = getNilDepreciation(asset, month);

            depreciationProceeds.setDepreciation(depreciation)
                    .setNetBookValue(new UnModifiedNetBookValue(asset, month).getNetBookValue())
                    .setAccruedDepreciation(new UnModifiedAccruedDepreciation(asset, month).getAccruedDepreciation());
        } else {

            log.trace("The asset : {} has passed the frontal Business rules filter, initiating configuration"
                    + "registry for category : {}", asset, asset.getCategory());

            // TODO agents to handle nonNilNetBookValueCriteria and DateAuthenticCriteria
            // logic
            depreciationAgentsHandler.sendRequest(asset, month, depreciationProceeds); // for today
        }
        return depreciationProceeds;
    }

    private void checkIfNull(FixedAsset asset, YearMonth month) {

        if (asset != null && month != null) {
            return;
        } else if (asset == null) {
            throw new RuntimeException("A null asset cannot be depreciated");
        } else {
            throw new RuntimeException("An asset cannot be depreciated in a null month");
        }
    }

    /**
     * Creates a depreciation object whose depreciation is Zero relative to the
     * fixedAsset item given and the depreciation period
     *
     * @param asset              FixedAsset with no depreciation for the period
     * @param depreciationPeriod Depreciation period for which depreciation is nil
     * @return NilDepreciation
     */
    private Depreciation getNilDepreciation(FixedAsset asset, YearMonth depreciationPeriod) {
        return new NilDepreciation(moneyProperties, asset, depreciationPeriod).getNilDepreciation();
    }

    public Depreciation getDepreciation() {
        return depreciation;
    }

    /**
     * {@inheritDoc}
     */
    public void setDepreciation(Depreciation depreciation) {
        this.depreciation = depreciation;
    }

    public DepreciationProceeds getDepreciationProceeds() {
        return depreciationProceeds;
    }

    public DepreciationExecutorImpl setDepreciationProceeds(DepreciationProceeds depreciationProceeds) {
        this.depreciationProceeds = depreciationProceeds;
        return this;
    }
}

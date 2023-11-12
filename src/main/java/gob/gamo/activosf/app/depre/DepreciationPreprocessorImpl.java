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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.convert.LocalDateToYearMonthConverter;
import gob.gamo.activosf.app.depre.exception.DepreciationExecutionException;
import gob.gamo.activosf.app.depre.model.FixedAsset;

import java.time.YearMonth;
import java.util.Objects;

/**
 * This component acts as middleware between calculated depreciation and actual
 * application of calculated depreciation which is supposed to check certain
 * business rules are maintained, for instance
 * the netBookValue is never to go below zero, and also that no asset is to be
 * depreciated prior to its purchase date. In the later the depreciation is
 * simply set to zero while in the former the
 * depreciation is set to be equivalent to the fixedAsset's netBookValue as at
 * the period of depreciation
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Component("depreciationPreprocessor")
public class DepreciationPreprocessorImpl implements DepreciationPreprocessor {

    private static final Logger log = LoggerFactory.getLogger(DepreciationPreprocessorImpl.class);
    private final LocalDateToYearMonthConverter localDateToYearMonthConverter;
    private final String moneyProperties;
    private YearMonth month;
    private FixedAsset asset;
    private Money depreciationAmount;

    /**
     * <p>
     * Constructor for DepreciationPreprocessorImpl.
     * </p>
     *
     * @param localDateToYearMonthConverter a
     *                                      {@link io.github.fasset.fasset.kernel.util.convert.LocalDateToYearMonthConverter}
     *                                      object.
     * @param moneyProperties               a
     *                                      {@link io.github.fasset.fasset.config.MoneyProperties}
     *                                      object.
     */
    @Autowired
    public DepreciationPreprocessorImpl(
            @Qualifier("localDateToYearMonthConverter") LocalDateToYearMonthConverter localDateToYearMonthConverter,
            String moneyProperties) {
        this.localDateToYearMonthConverter = localDateToYearMonthConverter;
        this.moneyProperties = moneyProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public YearMonth getMonth() {
        log.trace("Returning month : {}", month);
        return month;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FixedAsset getAsset() {
        log.trace("Returning fixedAsset : {}", asset);
        return asset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Money getDepreciationAmount() {
        log.trace("Returning depreciation amount : {}", depreciationAmount);
        return depreciationAmount;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the asset to be reviewed for depreciation
     */
    @Override
    public DepreciationPreprocessor setAsset(FixedAsset asset) {
        log.trace("Setting asset as : {}", asset);
        this.asset = asset;
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets depreciation period in months
     */
    @Override
    public DepreciationPreprocessor setMonth(YearMonth month) {
        log.trace("Setting month as : {}", month);
        this.month = month;
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets the amount of depreciation for review
     */
    @Override
    public DepreciationPreprocessor setDepreciationAmount(Money depreciationAmount) {
        log.trace("Setting depreciation amount as : {}", depreciationAmount);
        this.depreciationAmount = depreciationAmount;
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method ensures all properties are set and evaluated
     */
    @Override
    public DepreciationPreprocessor setProperties() {
        log.trace("Setting depreciation preprocessor properties...");

        if (asset == null || month == null) {
            String message = String.format(
                    "Exception encountered : Either the FixedAsset " + "instance %s or the month %s instance is null",
                    asset, month);
            throw new DepreciationExecutionException(message, new NullPointerException());
        } else {
            depreciationAmountRealignment(asset, month);
        }

        return this;
    }

    private void depreciationAmountRealignment(FixedAsset asset, YearMonth month) {
        log.trace("Calling the depreciation alignment algorithm....");
        depreciationTimingCheck(asset, month);
        depreciationRevaluation(asset);
    }

    private void depreciationRevaluation(FixedAsset asset) {
        log.trace("Performing re-evaluation of the depreciation amount...");
        if (asset.getPurchaseCost().isGreaterThan(Money.of(0.00, moneyProperties))) {
            if (asset.getNetBookValue().isLessThan(depreciationAmount)) {
                log.trace("The netBookValue of asset : {} is less that the depreciation amount", asset);
                if (asset.getNetBookValue().isEqualTo(Money.of(0.00, moneyProperties))) {
                    log.trace("The NetBookValue is zero, setting depreciation to zero....");
                   // No further processing required
                    this.depreciationAmount = Money.of(0.00, moneyProperties);
                    log.trace("The depreciation amount is today zero : {}", depreciationAmount);
                } else {
                    log.trace("Resetting depreciation amount to the remaining value of the netBookValue");
                    this.depreciationAmount = asset.getNetBookValue();
                    log.trace("Depreciation has been set to : {}", this.depreciationAmount);
                }
            }

        } else {

            log.warn(
                    "The asset has a negative purchase cost, meaning the "
                            + " asset is actually an adjustment, ideally we are to leave it alone...but a "
                            + "quick review of your book wouldn't hurt...");
            // If the purchase cost is less than zero we do nothing
        }
    }

    @SuppressWarnings("all")
    private void depreciationTimingCheck(FixedAsset asset, YearMonth month) {
        log.trace("Reviewing the depreciation timing for asset : {}, relative to the " + "month: {}", asset, month);
        if (localDateToYearMonthConverter.convert(Objects.requireNonNull(asset.getPurchaseDate())).isAfter(month)) {
            log.trace(
                    "The month of purchase of asset: {} comes later than the depreciation period : {}"
                            + "therefore we are resetting the depreciation formally calculated as : {} "
                            + "amount to zero",
                    asset, month, depreciationAmount);
            this.depreciationAmount = Money.of(0.00, moneyProperties);
            log.trace("Depreciation amount has been reset to zero : {}", depreciationAmount);
        }
        log.trace("The depreciation has passed the timing test and will be retained at : {}", depreciationAmount);
    }
}

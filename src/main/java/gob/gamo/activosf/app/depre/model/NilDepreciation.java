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
package gob.gamo.activosf.app.depre.model;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Object represents the concept of when no depreciation has occurred
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class NilDepreciation {

    private static final Logger log = LoggerFactory.getLogger(NilDepreciation.class);

    private int year;

    private int month;

    private YearMonth depreciationPeriod;

    private int fixedAssetId;

    private String category;

    private String solId;

    private double depreciation;

    private String moneyProperties;

    /**
     * <p>Constructor for NilDepreciation.</p>
     *
     * @param moneyProperties    a {@link io.github.fasset.fasset.config.MoneyProperties} object.
     * @param asset              a {@link io.github.fasset.fasset.model.FixedAsset} object.
     * @param depreciationPeriod a {@link java.time.YearMonth} object.
     */
    public NilDepreciation(String moneyProperties, FixedAsset asset, YearMonth depreciationPeriod) {
        this.moneyProperties = moneyProperties;
        this.month = depreciationPeriod.getMonthValue();
        this.year = depreciationPeriod.getYear();
        this.depreciationPeriod = depreciationPeriod;
        this.category = asset.getCategory();
        this.solId = asset.getSolId();
        this.fixedAssetId = asset.getId();
        this.depreciation = 0.00;
    }

    /**
     * <p>Constructor for NilDepreciation.</p>
     */
    public NilDepreciation() {
    }

    /**
     * Creates a depreciation object whose depreciation is Zero relative to the fixedAsset item given and the depreciation period
     *
     * @return Depreciation amount for the object
     */
    public Depreciation getNilDepreciation() {

        log.trace("Returning nil depreciation item relative to asset : {} for the" + " depreciation period : {}", fixedAssetId, depreciationPeriod);
        return new Depreciation().setMonth(month)
                                 .setYear(year)
                                 .setDepreciationPeriod(depreciationPeriod)
                                 .setCategory(category)
                                 .setSolId(solId)
                                 .setFixedAssetId(fixedAssetId)
                                 .setDepreciation(Money.of(0.00, "BBS"));
    }

    /**
     * <p>Getter for the field <code>year</code>.</p>
     *
     * @return a int.
     */
    public int getYear() {
        return year;
    }

    /**
     * <p>Setter for the field <code>year</code>.</p>
     *
     * @param year a int.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * <p>Getter for the field <code>month</code>.</p>
     *
     * @return a int.
     */
    public int getMonth() {
        return month;
    }

    /**
     * <p>Setter for the field <code>month</code>.</p>
     *
     * @param month a int.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * <p>Getter for the field <code>depreciationPeriod</code>.</p>
     *
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getDepreciationPeriod() {
        return depreciationPeriod;
    }

    /**
     * <p>Setter for the field <code>depreciationPeriod</code>.</p>
     *
     * @param depreciationPeriod a {@link java.time.YearMonth} object.
     */
    public void setDepreciationPeriod(YearMonth depreciationPeriod) {
        this.depreciationPeriod = depreciationPeriod;
    }

    /**
     * <p>Getter for the field <code>fixedAssetId</code>.</p>
     *
     * @return a int.
     */
    public int getFixedAssetId() {
        return fixedAssetId;
    }

    /**
     * <p>Setter for the field <code>fixedAssetId</code>.</p>
     *
     * @param fixedAssetId a int.
     */
    public void setFixedAssetId(int fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    /**
     * <p>Getter for the field <code>category</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCategory() {
        return category;
    }

    /**
     * <p>Setter for the field <code>category</code>.</p>
     *
     * @param category a {@link java.lang.String} object.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * <p>Getter for the field <code>solId</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSolId() {
        return solId;
    }

    /**
     * <p>Setter for the field <code>solId</code>.</p>
     *
     * @param solId a {@link java.lang.String} object.
     */
    public void setSolId(String solId) {
        this.solId = solId;
    }

    /**
     * <p>Getter for the field <code>depreciation</code>.</p>
     *
     * @return a double.
     */
    public double getDepreciation() {
        return depreciation;
    }

    /**
     * <p>Setter for the field <code>depreciation</code>.</p>
     *
     * @param depreciation a double.
     */
    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NilDepreciation that = (NilDepreciation) o;
        return year == that.year && month == that.month && fixedAssetId == that.fixedAssetId && Double.compare(that.depreciation, depreciation) == 0 &&
            Objects.equals(depreciationPeriod, that.depreciationPeriod) && Objects.equals(category, that.category) && Objects.equals(solId, that.solId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(year, month, depreciationPeriod, fixedAssetId, category, solId, depreciation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NilDepreciation{");
        sb.append("year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", depreciationPeriod=").append(depreciationPeriod);
        sb.append(", fixedAssetId=").append(fixedAssetId);
        sb.append(", category='").append(category).append('\'');
        sb.append(", solId='").append(solId).append('\'');
        sb.append(", depreciation=").append(depreciation);
        sb.append('}');
        return sb.toString();
    }
}

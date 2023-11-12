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

import org.hibernate.annotations.Type;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.YearMonth;
import java.util.Objects;

/**
 * This is a record of a depreciation for a given asset for a given month
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
//@Audited Too expensive
//@Entity(name = "Depreciation")
public class Depreciation  {

    private static final Logger log = LoggerFactory.getLogger(Depreciation.class);
    @Column(name = "id")
    private int id;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Column(name = "depreciation_period")
    private YearMonth depreciationPeriod;

    @Column(name = "fixed_asset_id")
    private int fixedAssetId;

    @Column(name = "category")
    private String category;

    @Column(name = "sol_id")
    private String solId;

    @Column
    private Money depreciation;

    /**
     * <p>Constructor for Depreciation.</p>
     */
    public Depreciation() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setMonth(int month) {
        log.trace("Setting month for depreciationId : {}, as = {}", getId(), month);
        this.month = month;
        return this;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setYear(int year) {
        log.trace("Setting year for depreciationId : {}, as = {}", getId(), depreciationPeriod);
        this.year = year;
        return this;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setDepreciationPeriod(YearMonth depreciationPeriod) {

        log.trace("Setting depreciation period for depreciationId : {}, as = {}", getId(), depreciationPeriod);
        this.depreciationPeriod = depreciationPeriod;
        return this;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setFixedAssetId(int fixedAssetId) {

        log.trace("Setting fixedAssetId for depreciationId : {}, as = {}", getId(), fixedAssetId);
        this.fixedAssetId = fixedAssetId;
        return this;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setCategory(String category) {

        log.trace("Setting the category for depreciationId : {}, as = {}", getId(), category);
        this.category = category;
        return this;
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
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setSolId(String solId) {

        log.trace("Setting the sol nomenclature for depreciationId : {}, as ={}", getId(), solId);
        this.solId = solId;
        return this;
    }

    /**
     * <p>Getter for the field <code>depreciation</code>.</p>
     *
     * @return a {@link org.javamoney.moneta.Money} object.
     */
    public Money getDepreciation() {
        return depreciation;
    }

    /**
     * <p>Setter for the field <code>depreciation</code>.</p>
     *
     * @param depreciation a {@link org.javamoney.moneta.Money} object.
     * @return a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    public Depreciation setDepreciation(Money depreciation) {

        log.trace("Setting the depreciation for depreciationId : {}, as = {}", getId(), depreciation);
        this.depreciation = depreciation;
        return this;
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
        if (!super.equals(o)) {
            return false;
        }
        Depreciation that = (Depreciation) o;
        return fixedAssetId == that.fixedAssetId && Objects.equals(that.depreciation, depreciation) && Objects.equals(depreciationPeriod, that.depreciationPeriod) &&
            Objects.equals(category, that.category) && Objects.equals(solId, that.solId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), depreciationPeriod, fixedAssetId, category, solId, depreciation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Depreciation{" + "depreciationPeriod=" + depreciationPeriod + ", fixedAssetId=" + fixedAssetId + ", category='" + category + '\'' + ", solId='" + solId + '\'' + ", depreciation=" +
            depreciation + '}';
    }
}

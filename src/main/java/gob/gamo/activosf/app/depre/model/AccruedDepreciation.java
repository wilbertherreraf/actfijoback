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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.text.MessageFormat;
import java.time.YearMonth;
import java.util.Objects;

//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"fixed_asset_id", "month", "sol_id", "category"})})

/**
 * This model represents a unit AccruedDepreciation in the repository
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
//@Audited
@Entity(name = "AccruedDepreciation")
public class AccruedDepreciation  {

    @Column(name = "fixed_asset_id")
    private int fixedAssetId;

    @Column(name = "month")
    private YearMonth month;

    @Column(name = "sol_id")
    private String solId;

    @Column(name = "category")
    private String category;

    @Column
    //@Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmount", parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "KES")})
    private Money accruedDepreciation;

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
     * @return a {@link io.github.fasset.fasset.model.AccruedDepreciation} object.
     */
    public AccruedDepreciation setFixedAssetId(int fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
        return this;
    }

    /**
     * <p>Getter for the field <code>month</code>.</p>
     *
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getMonth() {
        return month;
    }

    /**
     * <p>Setter for the field <code>month</code>.</p>
     *
     * @param month a {@link java.time.YearMonth} object.
     * @return a {@link io.github.fasset.fasset.model.AccruedDepreciation} object.
     */
    public AccruedDepreciation setMonth(YearMonth month) {
        this.month = month;
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
     * @return a {@link io.github.fasset.fasset.model.AccruedDepreciation} object.
     */
    public AccruedDepreciation setSolId(String solId) {
        this.solId = solId;
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
     * @return a {@link io.github.fasset.fasset.model.AccruedDepreciation} object.
     */
    public AccruedDepreciation setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * <p>Getter for the field <code>accruedDepreciation</code>.</p>
     *
     * @return a {@link org.javamoney.moneta.Money} object.
     */
    public Money getAccruedDepreciation() {
        return accruedDepreciation;
    }

    /**
     * <p>Setter for the field <code>accruedDepreciation</code>.</p>
     *
     * @param accruedDepreciation a {@link org.javamoney.moneta.Money} object.
     * @return a {@link io.github.fasset.fasset.model.AccruedDepreciation} object.
     */
    public AccruedDepreciation setAccruedDepreciation(Money accruedDepreciation) {
        this.accruedDepreciation = accruedDepreciation;
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
        AccruedDepreciation that = (AccruedDepreciation) o;
        return fixedAssetId == that.fixedAssetId && Objects.equals(that.accruedDepreciation, accruedDepreciation) && Objects.equals(month, that.month) && Objects.equals(solId, that.solId) &&
            Objects.equals(category, that.category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fixedAssetId, month, solId, category, accruedDepreciation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MessageFormat.format("AccruedDepreciation'{'fixedAssetId={0}, month={1}, solId=''{2}'', category=''{3}'', accruedDepreciation={4}'}'", fixedAssetId, month, solId, category,
                                    accruedDepreciation);
    }
}

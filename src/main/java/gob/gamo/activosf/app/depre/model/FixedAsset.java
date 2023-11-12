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

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * Main representation of a fixed asset in the server
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Entity(name = "FixedAsset")
// @Audited
public class FixedAsset implements Serializable, Comparable<FixedAsset> {

    private static final Logger log = LoggerFactory.getLogger(FixedAsset.class);
    @Column(name = "id")
    private int id;

    @Column(name = "sol_id")
    private String solId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "asset_description")
    private String assetDescription;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "category")
    private String category;

    @Column
    private Money purchaseCost;

    @Column

    private Money netBookValue;

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     */
    public FixedAsset() {
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     * @param category         a {@link java.lang.String} object.
     * @param solId            a {@link java.lang.String} object.
     * @param purchaseDate     a {@link java.time.LocalDate} object.
     * @param barcode          a {@link java.lang.String} object.
     * @param netBookValue     a {@link org.javaMoney.moneta.Money} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId,
            LocalDate purchaseDate, String barcode, Money netBookValue) {
        this(assetDescription, purchaseCost, category, solId, purchaseDate, barcode);
        this.netBookValue = netBookValue;
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     * @param category         a {@link java.lang.String} object.
     * @param solId            a {@link java.lang.String} object.
     * @param purchaseDate     a {@link java.time.LocalDate} object.
     * @param barcode          a {@link java.lang.String} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId,
            LocalDate purchaseDate, String barcode) {
        this(assetDescription, purchaseCost, category, solId, purchaseDate);
        this.barcode = barcode;
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     * @param category         a {@link java.lang.String} object.
     * @param solId            a {@link java.lang.String} object.
     * @param purchaseDate     a {@link java.time.LocalDate} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId,
            LocalDate purchaseDate) {
        this(assetDescription, purchaseCost, category, solId);
        this.purchaseDate = purchaseDate;
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     * @param category         a {@link java.lang.String} object.
     * @param solId            a {@link java.lang.String} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost, String category, String solId) {
        this(assetDescription, purchaseCost, category);
        this.solId = solId;
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     * @param category         a {@link java.lang.String} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost, String category) {
        this(assetDescription, purchaseCost);
        this.category = category;
    }

    /**
     * <p>
     * Constructor for FixedAsset.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @param purchaseCost     a {@link org.javaMoney.moneta.Money} object.
     */
    public FixedAsset(String assetDescription, Money purchaseCost) {
        this.assetDescription = assetDescription;
        this.purchaseCost = purchaseCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * /**
     * <p>
     * Getter for the field <code>solId</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSolId() {
        return solId;
    }

    /**
     * <p>
     * Setter for the field <code>solId</code>.
     * </p>
     *
     * @param solId a {@link java.lang.String} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setSolId(String solId) {
        this.solId = solId;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>barcode</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * <p>
     * Setter for the field <code>barcode</code>.
     * </p>
     *
     * @param barcode a {@link java.lang.String} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>assetDescription</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAssetDescription() {
        return assetDescription;
    }

    /**
     * <p>
     * Setter for the field <code>assetDescription</code>.
     * </p>
     *
     * @param assetDescription a {@link java.lang.String} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>purchaseDate</code>.
     * </p>
     *
     * @return a {@link java.time.LocalDate} object.
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * <p>
     * Setter for the field <code>purchaseDate</code>.
     * </p>
     *
     * @param purchaseDate a {@link java.time.LocalDate} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>category</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCategory() {
        return category;
    }

    /**
     * <p>
     * Setter for the field <code>category</code>.
     * </p>
     *
     * @param category a {@link java.lang.String} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>purchaseCost</code>.
     * </p>
     *
     * @return a {@link org.javaMoney.moneta.Money} object.
     */
    public Money getPurchaseCost() {
        return purchaseCost;
    }

    /**
     * <p>
     * Setter for the field <code>purchaseCost</code>.
     * </p>
     *
     * @param purchaseCost a {@link org.javaMoney.moneta.Money} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setPurchaseCost(Money purchaseCost) {

        log.trace("Setting the purchaseCost for fixedAssetId : {} ,as = {}", getId(), purchaseCost);
        this.purchaseCost = purchaseCost;
        return this;
    }

    /**
     * <p>
     * Getter for the field <code>netBookValue</code>.
     * </p>
     *
     * @return a {@link org.javaMoney.moneta.Money} object.
     */
    public Money getNetBookValue() {
        return netBookValue;
    }

    /**
     * <p>
     * Setter for the field <code>netBookValue</code>.
     * </p>
     *
     * @param netBookValue a {@link org.javaMoney.moneta.Money} object.
     * @return a {@link io.github.fasset.fasset.model.FixedAsset} object.
     */
    public FixedAsset setNetBookValue(Money netBookValue) {

        log.trace("Setting NBV for assetId : {}, as = {}", getId(), netBookValue);
        this.netBookValue = netBookValue;
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
        FixedAsset that = (FixedAsset) o;
        return Objects.equals(solId, that.solId) && Objects.equals(barcode, that.barcode)
                && Objects.equals(assetDescription, that.assetDescription) &&
                Objects.equals(purchaseDate, that.purchaseDate) && Objects.equals(category, that.category)
                && Objects.equals(purchaseCost, that.purchaseCost) &&
                Objects.equals(netBookValue, that.netBookValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), solId, barcode, assetDescription, purchaseDate, category, purchaseCost,
                netBookValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "FixedAsset'{'solId=''{0}'', barcode=''{1}'', assetDescription=''{2}'', purchaseDate={3}, category=''{4}'', purchaseCost={5}, netBookValue={6}'}'",
                solId, barcode,
                assetDescription, purchaseDate, category, purchaseCost, netBookValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(FixedAsset o) {

        return Comparator.comparing(FixedAsset::getSolId).thenComparing(FixedAsset::getCategory)
                .thenComparing(FixedAsset::getPurchaseDate).thenComparing(FixedAsset::getPurchaseCost).compare(this, o);
    }

}

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

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;

/**
 * DTO to carry all depreciations for each month of a year for a given category
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class MonthlyCategoryDepreciationDTO {

    private static final Logger log = LoggerFactory.getLogger(MonthlyCategoryDepreciationDTO.class);

    private String categoryName;
    private Integer year;
    private Double jan;
    private Double feb;
    private Double mar;
    private Double apr;
    private Double may;
    private Double jun;
    private Double jul;
    private Double aug;
    private Double sep;
    private Double oct;
    private Double nov;
    private Double dec;

    /**
     * <p>Constructor for MonthlyCategoryDepreciationDTO.</p>
     *
     * @param categoryName a {@link java.lang.String} object.
     * @param year         a {@link java.lang.Integer} object.
     * @param jan          a {@link javax.money.MonetaryAmount} object.
     * @param feb          a {@link javax.money.MonetaryAmount} object.
     * @param mar          a {@link javax.money.MonetaryAmount} object.
     * @param apr          a {@link javax.money.MonetaryAmount} object.
     * @param may          a {@link javax.money.MonetaryAmount} object.
     * @param jun          a {@link javax.money.MonetaryAmount} object.
     * @param jul          a {@link javax.money.MonetaryAmount} object.
     * @param aug          a {@link javax.money.MonetaryAmount} object.
     * @param sep          a {@link javax.money.MonetaryAmount} object.
     * @param oct          a {@link javax.money.MonetaryAmount} object.
     * @param nov          a {@link javax.money.MonetaryAmount} object.
     * @param dec          a {@link javax.money.MonetaryAmount} object.
     */
    public MonthlyCategoryDepreciationDTO(String categoryName, Integer year, MonetaryAmount jan, MonetaryAmount feb, MonetaryAmount mar, MonetaryAmount apr, MonetaryAmount may, MonetaryAmount jun,
                                          MonetaryAmount jul, MonetaryAmount aug, MonetaryAmount sep, MonetaryAmount oct, MonetaryAmount nov, MonetaryAmount dec) {
        this.categoryName = categoryName;
        this.year = year;
        this.jan = jan.getNumber().doubleValue();
        this.feb = feb.getNumber().doubleValue();
        this.mar = mar.getNumber().doubleValue();
        this.apr = apr.getNumber().doubleValue();
        this.may = may.getNumber().doubleValue();
        this.jun = jun.getNumber().doubleValue();
        this.jul = jul.getNumber().doubleValue();
        this.aug = aug.getNumber().doubleValue();
        this.sep = sep.getNumber().doubleValue();
        this.oct = oct.getNumber().doubleValue();
        this.nov = nov.getNumber().doubleValue();
        this.dec = dec.getNumber().doubleValue();
    }

    /**
     * <p>Getter for the field <code>categoryName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCategoryName() {
        log.trace("Returning categoryName : {}", categoryName);
        return categoryName;
    }

    /**
     * <p>Setter for the field <code>categoryName</code>.</p>
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setCategoryName(String categoryName) {
        log.trace("Setting categoryName : {}", categoryName);
        this.categoryName = categoryName;
        return this;
    }

    /**
     * <p>Getter for the field <code>year</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getYear() {

        log.trace("Returning year : {}", year);
        return year == null ? 0 : year;
    }

    /**
     * <p>Setter for the field <code>year</code>.</p>
     *
     * @param year a {@link java.lang.Integer} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setYear(Integer year) {
        log.trace("Setting year : {}", year);
        this.year = year;
        return this;
    }

    /**
     * <p>Getter for the field <code>jan</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getJan() {
        log.trace("Returning jan : {}", jan);
        return jan == null ? 0 : jan;
    }

    /**
     * <p>Setter for the field <code>jan</code>.</p>
     *
     * @param jan a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setJan(Double jan) {
        log.trace("Setting jan : {}", jan);
        this.jan = jan;
        return this;
    }

    /**
     * <p>Getter for the field <code>feb</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getFeb() {
        log.trace("Returning feb : {}", feb);
        return feb == null ? 0 : feb;
    }

    /**
     * <p>Setter for the field <code>feb</code>.</p>
     *
     * @param feb a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setFeb(Double feb) {
        log.trace("Setting feb : {}", feb);
        this.feb = feb;
        return this;
    }

    /**
     * <p>Getter for the field <code>mar</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getMar() {
        log.trace("Returning mar : {}", mar);
        return mar == null ? 0 : mar;
    }

    /**
     * <p>Setter for the field <code>mar</code>.</p>
     *
     * @param mar a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setMar(Double mar) {
        log.trace("Setting mar : {}", mar);
        this.mar = mar;
        return this;
    }

    /**
     * <p>Getter for the field <code>apr</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getApr() {
        log.trace("Returning apr : {}", apr);
        return apr == null ? 0 : apr;
    }

    /**
     * <p>Setter for the field <code>apr</code>.</p>
     *
     * @param apr a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setApr(Double apr) {
        log.trace("Setting apr : {}", apr);
        this.apr = apr;
        return this;
    }

    /**
     * <p>Getter for the field <code>may</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getMay() {
        log.trace("Returning may : {}", may);
        return may;
    }

    /**
     * <p>Setter for the field <code>may</code>.</p>
     *
     * @param may a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setMay(Double may) {
        log.trace("Setting may : {}", may);
        this.may = may;
        return this;
    }

    /**
     * <p>Getter for the field <code>jun</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getJun() {
        log.trace("Returning jun : {}", jun);
        return jun == null ? 0 : jun;
    }

    /**
     * <p>Setter for the field <code>jun</code>.</p>
     *
     * @param jun a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setJun(Double jun) {
        log.trace("Setting jun : {}", jun);
        this.jun = jun;
        return this;
    }

    /**
     * <p>Getter for the field <code>jul</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getJul() {
        log.trace("Returning jul : {}", jul);
        return jul == null ? 0 : jul;
    }

    /**
     * <p>Setter for the field <code>jul</code>.</p>
     *
     * @param jul a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setJul(Double jul) {
        log.trace("Setting jul : {}", jul);
        this.jul = jul;
        return this;
    }

    /**
     * <p>Getter for the field <code>aug</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getAug() {
        log.trace("Returning aug : {}", aug);
        return aug == null ? 0 : aug;
    }

    /**
     * <p>Setter for the field <code>aug</code>.</p>
     *
     * @param aug a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setAug(Double aug) {
        log.trace("Setting aug : {}", aug);
        this.aug = aug;
        return this;
    }

    /**
     * <p>Getter for the field <code>sep</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getSep() {
        log.trace("Returning sep : {}", sep);
        return sep == null ? 0 : sep;
    }

    /**
     * <p>Setter for the field <code>sep</code>.</p>
     *
     * @param sep a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setSep(Double sep) {
        log.trace("Setting sep : {}", sep);
        this.sep = sep;
        return this;
    }

    /**
     * <p>Getter for the field <code>oct</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getOct() {
        log.trace("Returning oct : {}", oct);
        return oct == null ? 0 : oct;
    }

    /**
     * <p>Setter for the field <code>oct</code>.</p>
     *
     * @param oct a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setOct(Double oct) {
        log.trace("Setting oct : {}", oct);
        this.oct = oct;
        return this;
    }

    /**
     * <p>Getter for the field <code>nov</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getNov() {
        log.trace("Returning nov : {}", nov);
        return nov == null ? 0 : nov;
    }

    /**
     * <p>Setter for the field <code>nov</code>.</p>
     *
     * @param nov a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setNov(Double nov) {
        log.trace("Setting nov : {}", nov);
        this.nov = nov;
        return this;
    }

    /**
     * <p>Getter for the field <code>dec</code>.</p>
     *
     * @return a {@link java.lang.Double} object.
     */
    public Double getDec() {
        log.trace("Returning dec : {}", dec);
        return dec == null ? 0 : dec;
    }

    /**
     * <p>Setter for the field <code>dec</code>.</p>
     *
     * @param dec a {@link java.lang.Double} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.model.MonthlyCategoryDepreciationDTO} object.
     */
    public MonthlyCategoryDepreciationDTO setDec(Double dec) {
        log.trace("Setting dec : {}", dec);
        this.dec = dec;
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
        MonthlyCategoryDepreciationDTO that = (MonthlyCategoryDepreciationDTO) o;
        return com.google.common.base.Objects.equal(categoryName, that.categoryName) && com.google.common.base.Objects.equal(year, that.year) && com.google.common.base.Objects.equal(jan, that.jan) &&
            com.google.common.base.Objects.equal(feb, that.feb) && com.google.common.base.Objects.equal(mar, that.mar) && com.google.common.base.Objects.equal(apr, that.apr) &&
            com.google.common.base.Objects.equal(may, that.may) && com.google.common.base.Objects.equal(jun, that.jun) && com.google.common.base.Objects.equal(jul, that.jul) &&
            com.google.common.base.Objects.equal(aug, that.aug) && com.google.common.base.Objects.equal(sep, that.sep) && com.google.common.base.Objects.equal(oct, that.oct) &&
            com.google.common.base.Objects.equal(nov, that.nov) && com.google.common.base.Objects.equal(dec, that.dec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(categoryName, year, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("categoryName", categoryName)
                          .add("year", year)
                          .add("jan", jan)
                          .add("feb", feb)
                          .add("mar", mar)
                          .add("apr", apr)
                          .add("may", may)
                          .add("jun", jun)
                          .add("jul", jul)
                          .add("aug", aug)
                          .add("sep", sep)
                          .add("oct", oct)
                          .add("nov", nov)
                          .add("dec", dec)
                          .toString();
    }
}

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

/**
 * Default depreciation for each month of the year for a given asset
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class NilMonthlyAssetDepreciationDTO extends MonthlyAssetDepreciationDTO {

    /**
     * <p>Constructor for NilMonthlyAssetDepreciationDTO.</p>
     *
     * @param assetId a {@link java.lang.Integer} object.
     * @param year    a {@link java.lang.Integer} object.
     */
    public NilMonthlyAssetDepreciationDTO(Integer assetId, Integer year) {
        super();
        super.setAssetId(assetId).setYear(year);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getAssetId() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getYear() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getJan() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getFeb() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getMar() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getApr() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getMay() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getJun() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getJul() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getAug() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getSep() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getOct() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getNov() {
        return 0.00;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getDec() {
        return 0.00;
    }

}

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

import java.time.YearMonth;

import gob.gamo.activosf.app.depre.model.Depreciation;
import gob.gamo.activosf.app.depre.model.FixedAsset;

/**
 * This interface manages the excecution of Depreciation for a given Asset in a given month
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface DepreciationExecutor {

    /**
     * Returns a Depreciation object given the fixed asset, and updates the fixed asset with the new net book value and the month of depreciation
     *
     * @param asset {@link io.github.fasset.fasset.model.FixedAsset} to be depreciated
     * @param month the month for which we are calculating depreciation
     * @return {@link io.github.fasset.fasset.model.Depreciation} object
     */
    DepreciationProceeds getDepreciation(FixedAsset asset, YearMonth month);

    /**
     * <p>setDepreciation.</p>
     *
     * @param depreciation a {@link io.github.fasset.fasset.model.Depreciation} object.
     */
    void setDepreciation(Depreciation depreciation);
}

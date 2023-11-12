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

import gob.gamo.activosf.app.depre.model.FixedAsset;

/**
 * Container for agents through which request is passed
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface DepreciationAgentsHandler {

    /**
     * <p>sendRequest.</p>
     *
     * @param asset                a {@link io.github.fasset.fasset.model.FixedAsset} object.
     * @param month                a {@link java.time.YearMonth} object.
     * @param depreciationProceeds a {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds} object.
     */
    void sendRequest(FixedAsset asset, YearMonth month, DepreciationProceeds depreciationProceeds);

}

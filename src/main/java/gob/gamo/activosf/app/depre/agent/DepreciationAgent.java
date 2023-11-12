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
package gob.gamo.activosf.app.depre.agent;


import java.time.YearMonth;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.model.Depreciation;
import gob.gamo.activosf.app.depre.model.FixedAsset;

/**
 * Executes the actual depreciation upon invocation by the DepreciationExecutor
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface DepreciationAgent extends Agent<Depreciation> {

    /**
     * {@inheritDoc}
     * <p>
     * This method calculates depreciation and updates internal variables from which the depreciation variables can be drawn
     */
    Depreciation invoke(FixedAsset asset, YearMonth month, DepreciationProceeds proceeds);
}

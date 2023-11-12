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
package gob.gamo.activosf.app.depre.service;


import java.util.List;

import gob.gamo.activosf.app.depre.model.NetBookValue;

/**
 * Service for data retrieval from database for {@link io.github.fasset.fasset.model.NetBookValue}
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface NetBookValueService {

    /**
     * Saves the {@link io.github.fasset.fasset.model.NetBookValue} object in the param
     *
     * @param netBookValue entity to be save in the repository
     */
    void saveNetBookValue(NetBookValue netBookValue);

    /**
     * Saves a {@link java.util.List} collection of all items passed in the parameter to the {@link io.github.fasset.fasset.repository.NetBookValueRepository}
     *
     * @param netBookValues Collection of netBookValue entities to be saved in the repository
     */
    void saveAllNetBookValueItems(List<? extends NetBookValue> netBookValues);


}

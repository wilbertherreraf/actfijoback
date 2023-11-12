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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.depre.model.NetBookValue;
import gob.gamo.activosf.app.depre.repository.NetBookValueRepository;

import java.util.List;

/**
 * Implements the {@link io.github.fasset.fasset.service.NetBookValueService} interface saving and retrieving {@link io.github.fasset.fasset.model.NetBookValue} records into the {@link
 * io.github.fasset.fasset.repository.NetBookValueRepository}
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Service("netBookValueService")
@Transactional
public class NetBookValueServiceImpl implements NetBookValueService {


    private final NetBookValueRepository netBookValueRepository;

    /**
     * <p>Constructor for NetBookValueServiceImpl.</p>
     *
     * @param netBookValueRepository a {@link io.github.fasset.fasset.repository.NetBookValueRepository} object.
     */
    @Autowired
    public NetBookValueServiceImpl(@Qualifier("netBookValueRepository") NetBookValueRepository netBookValueRepository) {
        this.netBookValueRepository = netBookValueRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves the {@link NetBookValue} object in the param
     */
    @Override
    public void saveNetBookValue(NetBookValue netBookValue) {

        netBookValueRepository.save(netBookValue);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves a {@link List} collection of all items passed in the parameter to the {@link NetBookValueRepository}
     */
    @Override
    public void saveAllNetBookValueItems(List<? extends NetBookValue> netBookValues) {

        netBookValueRepository.saveAll(netBookValues);

    }

}

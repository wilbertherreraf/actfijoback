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

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.depre.model.AccruedDepreciation;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.repository.AccruedDepreciationRepository;

import java.time.YearMonth;
import java.util.List;

/**
 * {@link io.github.fasset.fasset.service.AccruedDepreciationService} implementation
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Service("accruedDepreciationService")
@Transactional
public class AccruedDepreciationServiceImpl implements AccruedDepreciationService {

    private static final Logger log = LoggerFactory.getLogger(AccruedDepreciationServiceImpl.class);


    private final AccruedDepreciationRepository accruedDepreciationRepository;

    /**
     * <p>Constructor for AccruedDepreciationServiceImpl.</p>
     *
     * @param accruedDepreciationRepository a {@link io.github.fasset.fasset.repository.AccruedDepreciationRepository} object.
     */
    @Autowired
    public AccruedDepreciationServiceImpl(@Qualifier("accruedDepreciationRepository") AccruedDepreciationRepository accruedDepreciationRepository) {
        this.accruedDepreciationRepository = accruedDepreciationRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the accruedDepreciationAmount for a fixed asset  and month given as param. This will give the accrued depreciation up to the previous month
     */
    @Cacheable("accruedDepreciationForAssets")
    @Override
    public Money getAccruedDepreciationForAsset(FixedAsset asset, YearMonth month) {

        log.debug("Fetching the AccruedDepreciation for assetId : {}, for the month : {}", asset.getId(), month);

        //FIXME this query is returning nulls
        return accruedDepreciationRepository.findByFixedAssetIdAndMonthBefore(asset.getId(), month).getAccruedDepreciation();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves the {@link AccruedDepreciation} object given in the parameter
     */
    @Override
    public void saveAccruedDepreciation(AccruedDepreciation accruedDepreciation) {

        log.debug("Saving AccruedDepreciationId : {} into the AccruedDepreciationRepository", accruedDepreciation);

        accruedDepreciationRepository.save(accruedDepreciation);
    }


    /**
     * {@inheritDoc}
     * <p>
     * Saves a {@link List} collection of {@link AccruedDepreciation} items
     */
    @Override
    public void saveAllAccruedDepreciationRecords(List<? extends AccruedDepreciation> items) {

        log.info("Saving a collection of : {} accruedDepreciation items", items.size());

        accruedDepreciationRepository.saveAll(items);
    }
}

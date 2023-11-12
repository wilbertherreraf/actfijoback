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
package gob.gamo.activosf.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import gob.gamo.activosf.app.depre.util.ConcurrentList;
import gob.gamo.activosf.app.domain.CategoryConfiguration;
import gob.gamo.activosf.app.repository.CategoryConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@link io.github.fasset.fasset.service.CategoryBriefService} implementation
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@Service("categoryConfigurationService")
@RequiredArgsConstructor
public class CategoryConfigurationServiceImpl implements CategoryConfigurationService {
    private final CategoryConfigurationRepository categoryConfigurationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryConfiguration> getAllCategoryConfigurations() {
        log.trace("Fetching a list of all categories");
        List<CategoryConfiguration> categoryConfigurations = ConcurrentList.newList();

        categoryConfigurationRepository.findAll().forEach(c -> {
            log.trace("Adding category {} to category list", c);
            categoryConfigurations.add(c);
        });

        return categoryConfigurations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCategoryConfiguration(CategoryConfiguration fixedAssetCategory) {
        categoryConfigurationRepository.save(fixedAssetCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Cacheable("categoryConfigurationsById")
    @Override
    public CategoryConfiguration getCategoryConfigurationById(int id) {
        return categoryConfigurationRepository.findById(id).get();
    }

    /**
     * {@inheritDoc}
     */
    @Cacheable("categoryConfigurationsByNames")
    @Override
    public CategoryConfiguration getCategoryByName(String categoryName) {
        return categoryConfigurationRepository.findFirstByDesignation(categoryName);
    }

    /**
     * {@inheritDoc}
     */
    @Cacheable("depreciationRatesByCategoryNames")
    @Override
    public double getDepreciationRateByCategoryName(String categoryName) {
        return getCategoryByName(categoryName).getDepreciationRate();
    }
}

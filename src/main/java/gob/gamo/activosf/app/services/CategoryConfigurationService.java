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

import java.util.List;

import gob.gamo.activosf.app.domain.CategoryConfiguration;

/**
 * Service for data retrieval from database for
 * {@link gob.gamo.activosf.app.domain.fasset.fasset.model.CategoryConfiguration}
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public interface CategoryConfigurationService {

    /**
     * <p>
     * getAllCategoryConfigurations.
     * </p>
     *
     * @return Return all fixed assets categories
     */
    List<CategoryConfiguration> getAllCategoryConfigurations();

    /**
     * <p>
     * saveCategoryConfiguration.
     * </p>
     *
     * @param fixedAssetCategory to be saved to fixedAssetCategory repository
     */
    void saveCategoryConfiguration(CategoryConfiguration fixedAssetCategory);

    /**
     * <p>
     * getCategoryConfigurationById.
     * </p>
     *
     * @param id a int.
     * @return a
     *         {@link gob.gamo.activosf.app.domain.fasset.fasset.model.CategoryConfiguration}
     *         object.
     */
    CategoryConfiguration getCategoryConfigurationById(int id);

    /**
     * <p>
     * getCategoryByName.
     * </p>
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a
     *         {@link gob.gamo.activosf.app.domain.fasset.fasset.model.CategoryConfiguration}
     *         object.
     */
    CategoryConfiguration getCategoryByName(String categoryName);

    /**
     * <p>
     * getDepreciationRateByCategoryName.
     * </p>
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a double.
     */
    double getDepreciationRateByCategoryName(String categoryName);
}

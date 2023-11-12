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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.model.nil.NilCategoryConfiguration;
import gob.gamo.activosf.app.domain.CategoryConfiguration;
import gob.gamo.activosf.app.services.CategoryConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Using spring frameworks IOC container, this bean has a map into which the
 * categoryConfigurations are added as they are in the database. The bean also
 * exposes logic for programmatically adding more
 * configurations or editing existing ones. <br>
 * In practixe once invoked and constructed the configuration registry can be
 * read as shown in the following example
 * 
 * <pre>
 *     {@code
 * log.trace("Calculating depreciation for fixedAsset {}", asset);
 *
 * String categoryName = asset.getCategory();
 *
 * log.trace("Fetching categoryConfiguration instance from repository for designation : {}", categoryName);
 *
 * CategoryConfiguration configuration = categoryConfigurationRegistry.getCategoryConfiguration(categoryName);
 * }
 * </pre>
 * <p>
 * <br>
 * Once the configuration has been obtained the program can continue to run
 * using the specific parameters designed for a given category
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@Component("categoryConfigurationRegistry")
@RequiredArgsConstructor
public class CategoryConfigurationRegistry {

    private final CategoryConfigurationService categoryConfigurationService;
    private final Map<String, CategoryConfiguration> categoryConfigurationMap = new ConcurrentHashMap<>();

    /**
     * <p>
     * getCategoryConfiguration.
     * </p>
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a
     *         {@link gob.gamo.activosf.app.domain.fasset.fasset.model.CategoryConfiguration}
     *         object.
     */
    public CategoryConfiguration getCategoryConfiguration(String categoryName) {

        CategoryConfiguration categoryConfiguration;

        if (categoryConfigurationRegistryDoesNotContain(categoryName)) {
            updateConfigurationRegistry();
        }

        if (categoryConfigurationRegistryDoesNotContain(categoryName)) {
            log.error("The category Named : {} has not been configured in the category configuration repo. Please "
                    + "check your category configurations listing...", categoryName);
            categoryConfiguration = new NilCategoryConfiguration().getCategoryConfiguration();
        }

        categoryConfiguration = categoryConfigurationMap.get(categoryName);

        return categoryConfiguration;
    }

    void addCategoryConfiguration(String categoryName, CategoryConfiguration categoryConfiguration) {
        categoryConfigurationMap.put(categoryName, categoryConfiguration);
    }

    private boolean categoryConfigurationRegistryDoesNotContain(String categoryName) {
        return !categoryConfigurationMap.containsKey(categoryName);
    }

    boolean categoryConfigurationRegistryIsEmpty() {
        return categoryConfigurationMap.isEmpty();
    }

    @PostConstruct // still debugging...
    private void updateConfigurationRegistry() {

        if (categoryConfigurationMap.isEmpty()) {

            log.trace("Refreshing the category configuration mapping...");

            categoryConfigurationService.getAllCategoryConfigurations().stream()
                    .map(CategoryConfiguration::getDesignation).forEach(categoryName -> {
                        log.trace("Registering category : {}", categoryName);
                        categoryConfigurationMap.put(categoryName,
                                categoryConfigurationService.getCategoryByName(categoryName));
                    });
        }

    }
}

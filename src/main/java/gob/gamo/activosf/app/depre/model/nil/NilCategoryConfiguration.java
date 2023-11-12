package gob.gamo.activosf.app.depre.model.nil;

import gob.gamo.activosf.app.domain.CategoryConfiguration;

/**
 * This object represents data improperly configured CategoryConfiguration
 * through the use of defaults
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class NilCategoryConfiguration {

    private final String designation;

    /**
     * The name of the depreciation logic
     */
    private final String depreciationLogic;

    /**
     * This is the item on which the depreciation rate is applied, as in either the
     * cost or the net book value
     */
    private final String deprecant;

    private final double depreciationRate;

    private final String categoryLedgerId;

    /**
     * <p>
     * Constructor for NilCategoryConfiguration.
     * </p>
     */
    public NilCategoryConfiguration() {
        this.designation = "Category";
        this.depreciationLogic = "DECLININGBALANCE";
        this.deprecant = "NETBOOKVALUE";
        this.depreciationRate = 0.00;
        this.categoryLedgerId = "0000000000";
    }

    /**
     * <p>
     * getCategoryConfiguration.
     * </p>
     *
     * @return a {@link io.github.fasset.fasset.model.CategoryConfiguration} object.
     */
    public CategoryConfiguration getCategoryConfiguration() {
        return new CategoryConfiguration(designation, depreciationLogic, deprecant, depreciationRate, categoryLedgerId);
    }
}

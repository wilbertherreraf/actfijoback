package gob.gamo.activosf.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gob.gamo.activosf.app.domain.CategoryConfiguration;

/**
 * This Repository extends the Spring JPA Template and has runtime-implentation
 * depending on the nature of the {@code Entity}
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Repository("categoryConfigurationRepository")
public interface CategoryConfigurationRepository extends JpaRepository<CategoryConfiguration, Integer> {

    /**
     * <p>
     * findFirstByDesignation.
     * </p>
     *
     * @param designation a {@link java.lang.String} object.
     * @return a
     *         {@link gob.gamo.activosf.app.domain.fasset.fasset.model.CategoryConfiguration}
     *         object.
     */
    CategoryConfiguration findFirstByDesignation(String designation);

}

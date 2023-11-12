package gob.gamo.activosf.app.depre.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;

/**
 * Repository for depreciationJobInstance
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Repository("depreciationJobInstanceRepository")
public interface DepreciationJobInstanceRepository extends JpaRepository<DepreciationJobInstance, Integer> {
    /**
     * <p>findAllByMonthBefore.</p>
     *
     * @param month a {@link java.time.YearMonth} object.
     * @return a {@link java.util.List} object.
     */
    List<DepreciationJobInstance> findAllByMonthBefore(YearMonth month);
}

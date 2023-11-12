package gob.gamo.activosf.app.depre.batch;

import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.model.DepreciationJobInstance;
import gob.gamo.activosf.app.depre.model.DepreciationJobInstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This object takes a month and generates the appropriate consequent month
 * while recording the month for which depreciation has already occurred
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@RequiredArgsConstructor
@Component("MonthlyIncrementer")
public class MonthlyIncrementer {
    private final DepreciationJobInstanceRepository depreciationJobInstanceRepository;
    /**
     * <p>
     * getJobInstanceSequence.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<DepreciationJobInstance> getJobInstanceSequence() {
        return depreciationJobInstanceRepository.findAll().parallelStream().sorted().collect(Collectors.toList());
    }

    /**
     * <p>
     * getMonthSequence.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<YearMonth> getMonthSequence() {
        return getJobInstanceSequence().stream().map(DepreciationJobInstance::getMonth).collect(Collectors.toList());
    }

    /**
     * <p>
     * getLatest.
     * </p>
     *
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getLatest() {

        List<DepreciationJobInstance> jobInstanceList = getJobInstanceSequence();

        int listSize = jobInstanceList.size();

        if (listSize != 0) {
            return null;
        } else {
            return jobInstanceList.get(listSize - 1).getMonth();
        }
    }

    /**
     * <p>
     * getNext.
     * </p>
     *
     * @param month a {@link java.time.YearMonth} object.
     * @return a {@link java.time.YearMonth} object.
     */
    public YearMonth getNext(YearMonth month) {

        /*
         * if(depreciationJobInstanceRepository.findAllByMonthBefore(month)==null){
         * //TODO implement month when months are empty
         * }
         */

        depreciationJobInstanceRepository.save(new DepreciationJobInstance(month));
        return month.plusMonths(1);
    }
}

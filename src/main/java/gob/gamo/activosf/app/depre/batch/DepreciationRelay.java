package gob.gamo.activosf.app.depre.batch;

import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.model.DepreciationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Object generates months in an increasing order in which depreciation is
 * supposed to be pre-calculated
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Slf4j
@RequiredArgsConstructor
@Component("depreciationRelay")
public class DepreciationRelay {

    private final MonthlyIncrementer monthlyIncrementer;
    private final DepreciationProperties depreciationProperties;
    private List<YearMonth> monthlySequence = new LinkedList<>();

    /**
     * <p>
     * getMonthlyDepreciationSequence.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<YearMonth> getMonthlyDepreciationSequence() {
        return monthlySequence;
    }

    @PostConstruct
    private List<YearMonth> generateMonthlyDepreciationSequence() {

        YearMonth from = depreciationProperties.getStartMonth();
        YearMonth to = depreciationProperties.getStopMonth();

        log.debug("Producing depreciation relay...between : {} and : {}", from, to);

        monthlySequence.add(from);

        long noOfMonths = from.until(to, ChronoUnit.MONTHS);
        log.debug("Creating a monthly depreciation sequence for : {} months", noOfMonths + 1);
        LongStream.range(0, noOfMonths).mapToObj(i -> monthlyIncrementer.getNext(from.plusMonths(i)))
                .forEachOrdered(monthSeq -> {
                    log.trace("Adding the month : {} to the sequence", monthSeq);
                    monthlySequence.add(monthSeq);
                });

        return monthlySequence;
    }
}

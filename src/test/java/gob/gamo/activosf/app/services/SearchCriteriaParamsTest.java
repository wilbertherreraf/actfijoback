package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Joiner;

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchOperation;
import gob.gamo.activosf.app.search.SpecSearchCriteria;
import gob.gamo.activosf.app.search.UserSpecification;
import gob.gamo.activosf.app.search.UserSpecificationsBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchCriteriaParamsTest {
    @Test
    public void dataQuery() {
        try {
            log.info("  =======> params query =========");

            List<String> paramsS = Arrays.asList("(asdf.firstName:john)",
                    "( firstName:john OR firstName:tom ) AND age>22", "firstName:john OR lastName:doe",
                    "( tab_colq:asd AND tab_colb:wwr and tabc_col1:*q3 sd* or tabf: asdf asdf )","firstName!john");

            paramsS.forEach(search -> {
                log.info("  =======> params {}", search);

                Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
                Matcher matcher = pattern.matcher(search + ",");
                log.info("params 1 ");
                final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
                while (matcher.find()) {
                    SearchCriteria sc = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                    params.add(sc);
                    log.info("Criteria {} {} {}", sc.getKey(), sc.getOperation(), sc.getValue());
                }

                String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
                pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
                matcher = pattern.matcher(search + ",");
                log.info("params 2 {}", operationSetExper);
                UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
                while (matcher.find()) {
                    log.info("Patern ==> !:{} 2:{} 3:{} 4:{} 5:{} 6:{}", matcher.group(1), matcher.group(2),
                            matcher.group(3), matcher.group(4), matcher.group(5));
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3),
                            matcher.group(5));

                    builder.getParams().forEach((cc) -> {
                        SpecSearchCriteria c = (SpecSearchCriteria) cc;
                        log.info("Builder {} [{}] {}", c.getKey(), c.getOperation(), c.getValue());
                    });
                }

                log.info("  params Spec ==>{}");
                // operationSetExper =
                // Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
                pattern = Pattern
                        .compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");

                matcher = pattern.matcher(search + ",");
                log.info("params 3 {}", operationSetExper);
                CriteriaParser parser = new CriteriaParser();
                
                Deque<?> deque = parser.parse(search);
                deque.forEach(r -> {
                    if (r instanceof SpecSearchCriteria){
                        SpecSearchCriteria c = (SpecSearchCriteria) r;
                        log.info("Deque {} [{}] {}", c.getKey(), c.getOperation(), c.getValue());
                    } else {
                        log.info("Deque ==> {}",r);
                    }
                });

                while (matcher.find()) {
                    log.info("Pattern3 ==> !:{} 2:{} 3:{} 4:{} 5:{} 6:{}", matcher.group(1), matcher.group(2),
                            matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
                }

                // log.info(" params 3 Spec ==>{}");
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
}

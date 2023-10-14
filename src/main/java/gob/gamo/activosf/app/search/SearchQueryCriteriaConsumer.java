package gob.gamo.activosf.app.search;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchQueryCriteriaConsumer<T> implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root<T> root;

    public SearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root<T> r) {
        super();
        this.predicate = predicate;
        this.builder = builder;
        this.root = r;
    }

    @Override
    public void accept(SearchCriteria param) {
        boolean isFirst = true;
        if (param.getChildren() != null && param.getChildren().size() > 0) {
            param.getChildren().forEach(clauseNvl1 -> {

                if (clauseNvl1.getOperation().equalsIgnoreCase("AND")) {
                    if (clauseNvl1.getChildren().size() > 0) {
                        Predicate pAnd = builder.conjunction();
                        for (SearchCriteria clause : clauseNvl1.getChildren()) {
                            log.info("ch ::=>  {} {} [{}] {}", clauseNvl1.getOperation(), clause.getKey(),
                                    clause.getOperation(),
                                    clause.getValue());
                            Predicate p = genPredicate(clause);
                            pAnd = builder.and(pAnd, p);
                        }
                        predicate = builder.and(predicate, pAnd);
                    }
                } else if (clauseNvl1.getOperation().equalsIgnoreCase("OR")) {
                    if (clauseNvl1.getChildren().size() > 0) {
                        Predicate pOr = builder.disjunction();
                        for (SearchCriteria rr : clauseNvl1.getChildren()) {
                            log.info("OR ch ::=>  {} {} [{}] {}", clauseNvl1.getOperation(), rr.getKey(),
                                    rr.getOperation(),
                                    rr.getValue());
                            Predicate p = genPredicate(rr);
                            pOr = builder.or(pOr, p);
                        }
                        predicate = builder.and(predicate, pOr);
                    }
                }
            });
        } else {
            genPredicate(param);
        }

    }

    public Predicate genPredicate(SearchCriteria param) {
        log.info("buscando attrib {} {}", param.getKey(), param.getValue());
        int idx = param.getKey().indexOf('.');
        boolean isJoin = idx > 0;
        String attrib = isJoin ? param.getKey().substring(idx + 1) : param.getKey();
        Expression<String> path = null;
        if (!isJoin) {
            path = root.get(param.getKey());
        } else {
            for (Join<T, ?> j : root.getJoins()) {
                path = j.get(attrib);
            }
        }
        log.info("buscando attrib {} PATH {}", attrib, path.getClass());
        if (param.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(path, param.getValue().toString());
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(path, param.getValue().toString());
        } else if (param.getOperation().equalsIgnoreCase("Nil")) {
            return builder.isNull(path);
        } else if (param.getOperation().equalsIgnoreCase("notNil")) {
            return builder.isNotNull(path);
        } else if (param.getOperation().equalsIgnoreCase("<>")) {
            return builder.notEqual(path, param.getValue());
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                String value = param.getValue().toString();
                log.info("valor strng {}; cl {}", value, path.getJavaType().getSimpleName());
                if (value.startsWith("*")) {
                    return builder.like(path, "%" + value.replaceAll("\\*", ""));
                } else if (value.endsWith("*")) {
                    return builder.like(path, value.replaceAll("\\*", "") + "%");
                } else if (value.startsWith("*") && value.endsWith("*")) {
                    return builder.like(path, "%" + value.replaceAll("\\*", "") + "%");
                } else {
                    log.info("valor strng {} 111111: {}", value, param.getValue());
                    return builder.equal(path, param.getValue());
                }
            } else {
                log.info("valor strng {} 2222222", param.getValue());
                return builder.equal(path, param.getValue());
            }
        }
        return null;
    }

    public Predicate genPredicateOr(SearchCriteria param) {
        int idx = param.getKey().indexOf('.');
        boolean isJoin = idx > 0;
        String attrib = isJoin ? param.getKey().substring(idx + 1) : param.getKey();
        Expression<String> path = null;
        if (!isJoin) {
            path = root.get(param.getKey());
        } else {
            for (Join<T, ?> j : root.getJoins()) {
                path = j.get(attrib);
            }
        }
        log.info("buscando attrib {} PATH {}", attrib, path.getClass());

        if (param.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(path, param.getValue().toString());
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(path, param.getValue().toString());
        } else if (param.getOperation().equalsIgnoreCase("Nil")) {
            return builder.isNull(path);

        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + param.getValue() + "%");
            } else {
                return builder.equal(path, param.getValue());
            }
        }
        return null;
    }

    public Predicate getPredicate() {
        return predicate;
    }
}
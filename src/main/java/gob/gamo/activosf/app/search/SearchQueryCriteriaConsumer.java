package gob.gamo.activosf.app.search;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchQueryCriteriaConsumer<T> implements Consumer<SearchCriteria> {

    private Predicate predicate0;
    private CriteriaBuilder builder;
    private Root<T> root;
    private List<Predicate> lp = new ArrayList<>();

    public SearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root<T> r) {
        super();
        this.predicate0 = predicate;
        this.builder = builder;
        this.root = r;
    }

    @Override
    public void accept(SearchCriteria param) {
        if (param == null) {
            return;
        }
        if (param.getChildren() != null && param.getChildren().size() > 0) {
            param.getChildren().stream()
                    .filter(p -> !StringUtils.isBlank(p.getOperation())) //
                    .forEach(clauseNvl1 -> {
                        if (clauseNvl1.getOperation().equalsIgnoreCase("AND")) {
                            if (clauseNvl1.getChildren().size() > 0) {
                                Predicate pAnd = null;
                                boolean crea = false;
                                for (SearchCriteria clause : clauseNvl1.getChildren().stream()
                                        .filter(p -> !StringUtils.isBlank(p.getOperation()))
                                        .toList()) {
                                    /*
                                     * log.info("ch ::=>  {} {} [{}] {}", clauseNvl1.getOperation(),
                                     * clause.getKey(),
                                     * clause.getOperation(),
                                     * clause.getValue());
                                     */
                                    Predicate p = genPredicate(clause);
                                    if (p != null) {
                                        if (!crea) {
                                            pAnd = builder.and(p);
                                            crea = true;
                                        } else {
                                            pAnd = builder.and(pAnd, p);
                                        }
                                    }
                                }

                                if (crea) lp.add(pAnd);
                            }
                        } else if (clauseNvl1.getOperation().equalsIgnoreCase("OR")) {
                            if (clauseNvl1.getChildren().size() > 0) {
                                Predicate pOr = null; // builder.and();// builder.disjunction();
                                boolean crea = false;
                                for (SearchCriteria rr : clauseNvl1.getChildren().stream()
                                        .filter(p -> !StringUtils.isBlank(p.getOperation()))
                                        .toList()) {
                                    /*
                                     * log.info("OR ch ::=>  {} {} [{}] {}", clauseNvl1.getOperation(), rr.getKey(),
                                     * rr.getOperation(),
                                     * rr.getValue());
                                     */
                                    Predicate p = genPredicate(rr);
                                    if (p != null) {
                                        if (pOr == null) {
                                            pOr = builder.or(p);
                                            crea = true;
                                        } else pOr = builder.or(pOr, p);
                                    }
                                }
                                if (crea) lp.add(pOr);
                            }
                        }
                    });
        } else {
            Predicate p = genPredicate(param);
            predicate0 = builder.and(predicate0, p);
        }
    }

    public Predicate genPredicate(SearchCriteria param) {
        if (StringUtils.isBlank(param.getKey()) || StringUtils.isBlank(param.getOperation())) {
            return null;
        }
        // log.info("buscando attrib {} {}", param.getKey(), param.getValue());
        int idx = param.getKey().indexOf('.');
        boolean isJoin = idx > 0;
        String alias = isJoin ? param.getKey().substring(0, idx) : param.getKey();
        String attrib = isJoin ? param.getKey().substring(idx + 1) : param.getKey();

        // log.info("buscando tab: {} col [{}] -> {} {}", alias, attrib, param.getKey(),
        // param.getValue());
        Expression<?> path = null;
        if (root.getAlias() != null && alias.equalsIgnoreCase(root.getAlias()) || !isJoin) {
            path = root.get(attrib);
        } else {
            for (Join<T, ?> j : root.getJoins()) {
                // log.info("Joins attrib[{}] -> alias: {} {}", attrib, j.getAlias(),
                // j.getAttribute().getName());
                if (j.getAlias() != null && alias.equalsIgnoreCase(j.getAlias())
                        || param.getKey()
                                .toLowerCase()
                                .startsWith(j.getAttribute().getName().toLowerCase())) {
                    path = j.get(attrib);
                    break;
                }
            }
        }
        if (path == null) {
            return null;
        }
        log.info(
                "buscando key: [{}]attrib {} PATH {} tp: {} value {}",
                param.getKey(),
                attrib,
                path.getClass(),
                path.getJavaType(),
                param.getValue());
        if (param.getOperation().equalsIgnoreCase(">")) {
            if (path.getJavaType() == String.class) {
                return builder.greaterThanOrEqualTo(
                        path.as(String.class), param.getValue().toString());
            } else if (path.getJavaType() == Integer.class) {
                Integer d = (Integer) param.getValue();
                return builder.greaterThanOrEqualTo(path.as(Integer.class), d);
            } else if (path.getJavaType() == Date.class || path.getJavaType() == Timestamp.class) {
                Date d = (Date) param.getValue();
                return builder.greaterThanOrEqualTo(path.as(Date.class), d);
            }
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            if (path.getJavaType() == String.class) {
                return builder.greaterThanOrEqualTo(
                        path.as(String.class), param.getValue().toString());
            } else if (path.getJavaType() == Integer.class) {
                Integer d = (Integer) param.getValue();
                return builder.greaterThanOrEqualTo(path.as(Integer.class), d);
            } else if (path.getJavaType() == Date.class || path.getJavaType() == Timestamp.class) {
                Date d = (Date) param.getValue();
                return builder.greaterThanOrEqualTo(path.as(Date.class), d);
            }
        } else if (param.getOperation().equalsIgnoreCase("Nil")
                || param.getOperation().equalsIgnoreCase("Null")) {
            return builder.isNull(path);
        } else if (param.getOperation().equalsIgnoreCase("notNil")
                || param.getOperation().equalsIgnoreCase("notNull")) {
            return builder.isNotNull(path);
        } else if (param.getOperation().equalsIgnoreCase("<>")) {
            return builder.notEqual(path, param.getValue());
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                if (param.getValue() == null)
                    return null;
                String value = param.getValue().toString();
                if (StringUtils.isBlank(value)) {
                    return null;
                }
                // log.info("valor strng {}; cl {}", value, path.getJavaType().getSimpleName());
                boolean isContains = value.startsWith("*") && value.endsWith("*");

                if (value.startsWith("*") && !isContains) {
                    value = value.toLowerCase();
                    return builder.like(builder.lower(path.as(String.class)), "%" + value.replaceAll("\\*", ""));
                } else if (value.endsWith("*") && !isContains) {
                    value = value.toLowerCase();
                    return builder.like(builder.lower(path.as(String.class)), value.replaceAll("\\*", "") + "%");
                } else if (isContains) {
                    value = value.toLowerCase();
                    return builder.like(builder.lower(path.as(String.class)), "%" + value.replaceAll("\\*", "") + "%");
                } else {
                    return builder.equal(path, param.getValue());
                }
            } else {
                return builder.equal(path, param.getValue());
            }
        }
        return null;
    }

    public Predicate getPredicate() {
        return predicate0;
    }

    public List<Predicate> getPredicates() {
        return lp;
    }
}

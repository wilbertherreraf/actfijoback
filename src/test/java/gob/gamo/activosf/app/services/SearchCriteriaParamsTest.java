package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.gson.Gson;

import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchOperation;
import gob.gamo.activosf.app.search.SpecSearchCriteria;
import gob.gamo.activosf.app.search.UserSpecification;
import gob.gamo.activosf.app.search.UserSpecificationsBuilder;
import gob.gamo.activosf.app.treeorg.Node;
import gob.gamo.activosf.app.treeorg.OrgHierarchy;
import gob.gamo.activosf.app.treeorg.OrgHierarchyInterface;
import gob.gamo.activosf.app.utils.JsonUtilsTest.Usuario1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchCriteriaParamsTest {
    @Test
    public void dataQuery() {
        try {
            log.info("  =======> params query =========");

            List<String> paramsS = Arrays.asList("(asdf.firstName:john)",
                    "( firstName:john OR firstName:tom ) AND age>22", "firstName:john OR lastName:doe",
                    "( tab_colq:asd AND tab_colb:wwr and tabc_col1:*q3 sd* or tabf: asdf asdf )", "firstName!john");

            paramsS.forEach(search -> {
                log.info("  =======> params {}", search);

                Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
                Matcher matcher = pattern.matcher(search + ",");
                log.info("params 1 ");
                final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
                /*
                 * while (matcher.find()) {
                 * SearchCriteria sc = new SearchCriteria(matcher.group(1), matcher.group(2),
                 * matcher.group(3));
                 * params.add(sc);
                 * log.info("Criteria {} {} {}", sc.getKey(), sc.getOperation(), sc.getValue());
                 * }
                 */

                String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
                pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
                matcher = pattern.matcher(search + ",");
                log.info("params 2 {}", operationSetExper);
                UserSpecificationsBuilder<?> builder = new UserSpecificationsBuilder<>();
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
                    if (r instanceof SpecSearchCriteria) {
                        SpecSearchCriteria c = (SpecSearchCriteria) r;
                        log.info("Deque {} [{}] {}", c.getKey(), c.getOperation(), c.getValue());
                    } else {
                        log.info("Deque ==> {}", r);
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

    @Test
    public void dataSearchParser() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("====== inicio txt ======");
            final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
            params.add(SearchCriteria.builder().id(11).key("nombre").operation(":").value("sd").build());
            params.add(SearchCriteria.builder().id(12).key("tipopers").operation(":").value(1).build());

            SearchCriteria s1 = SearchCriteria.builder().id(1).operation("OR").build();
            SearchCriteria s11 = SearchCriteria.builder().id(11).idPadre(1).operation("AND").padre(s1)
                    .build();
            SearchCriteria s12 = SearchCriteria.builder().id(12).idPadre(1).operation("OR").padre(s1)
                    .build();
            SearchCriteria s21_11 = SearchCriteria.builder().id(21).idPadre(11).key("nombre").operation(":").value("sd").padre(s11)
                    .build();
            SearchCriteria s22_11 = SearchCriteria.builder().id(22).idPadre(11).key("tipopers").operation(":").value(1).padre(s11)
                    .build();
            SearchCriteria s23_11 = SearchCriteria.builder().id(23).idPadre(11).key("nombre").operation(":").value("sd").padre(s11)
                    .build();
            SearchCriteria s25_12 = SearchCriteria.builder().id(25).idPadre(12).key("tipopers").operation(":").value(1).padre(s12)
                    .build();
            SearchCriteria s26_12 = SearchCriteria.builder().id(26).idPadre(12).key("tipopers").operation(":").value(1).padre(s12)
                    .build();

            String st = mapper.writeValueAsString(params);

            log.info("eeeeeeeeeeeee map {} {} {}", st);
            log.info("********* iniciooooooooooooooo  ");
            OrgHierarchyInterface<SearchCriteria> treeOrg = new OrgHierarchy<SearchCriteria>();
            treeOrg.hireOwner(new Node<SearchCriteria>(s1.getId(), s1));

            treeOrg.addNewChild(createNode(s11), s11.getIdPadre());
            treeOrg.addNewChild(createNode(s12), s12.getIdPadre());
            treeOrg.addNewChild(createNode(s21_11), s21_11.getIdPadre());
            treeOrg.addNewChild(createNode(s22_11), s22_11.getIdPadre());
            treeOrg.addNewChild(createNode(s23_11), s23_11.getIdPadre());
            treeOrg.addNewChild(createNode(s25_12), s25_12.getIdPadre());
            treeOrg.addNewChild(createNode(s26_12), s26_12.getIdPadre());

            Node<SearchCriteria> root = treeOrg.returnRoot();
            root.nivls().stream().forEach(l -> {
                /*
                 * Node<SearchCriteria> n = treeOrg.searchNode(Integer.valueOf(l));
                 * SearchCriteria sc = n.getValue();
                 */
                log.info("" + l);
            });

            LinkedList<Node<SearchCriteria>> nl = treeOrg.getbylevel(root, 3);
            nl.forEach(n -> {
                SearchCriteria sc = n.getValue();
                log.info(sc.getId() + " => " + sc.getOperation());
            });

            LinkedList<Node<SearchCriteria>> nt = root.childs();
            nt.forEach(n -> {
                boolean hasCh = n.childs().size() > 0;
                SearchCriteria sc = n.getValue();
                Node<SearchCriteria> padre = n.getParent();
                String operP = padre != null ? padre.getValue().getOperation() : "";
                if (hasCh) {
                    log.info(sc.getId() + "     ==> " + sc.getOperation() );
                } else {
                    log.info(sc.getId() + " ==> " + operP + " " + sc.getKey() + " " + sc.getOperation() + " "
                            + sc.getValue());
                }
            });
            String json = mapper.writeValueAsString(s1);
            log.info("json ROOT {}", json);

            json = mapper.writeValueAsString(s21_11);
            log.info("json map {}", json);   
            
            String inJson = "{\"idSearch\":\"09cec640-c0a2-45ca-831b-3d60a33cb8b2\",\"id\":21,\"key\":\"nombre\",\"operation\":\":\",\"value\":\"sd\",\"idPadre\":11,\"padre\":{\"idSearch\":\"eb7723ff-19c9-40ef-85a5-98fe7191809e\",\"id\":11,\"key\":null,\"operation\":\"AND\",\"value\":null,\"idPadre\":1,\"padre\":{\"idSearch\":\"331e2471-6fef-46a8-8fd2-e283889ab5c8\",\"id\":1,\"key\":null,\"operation\":\"OR\",\"value\":null,\"idPadre\":null,\"padre\":null,\"children\":[]},\"children\":[]},\"children\":[]}";
            String js = "{\"idSearch\":\"09cec640-c0a2-45ca-831b-3d60a33cb8b2\",\"id\":21,\"key\":\"nombre\",\"operation\":\":\",\"value\":\"sd\",\"idPadre\":11,\"padre\":{\"idSearch\":\"eb7723ff-19c9-40ef-85a5-98fe7191809e\",\"id\":11,\"operation\":\"AND\",\"idPadre\":1,\"padre\":{\"idSearch\":\"331e2471-6fef-46a8-8fd2-e283889ab5c8\",\"id\":1,\"operation\":\"OR\"}}}";

             Gson gson = new Gson();
             SearchCriteria sc = gson.fromJson(jsonval, SearchCriteria.class);
             log.info("Sc object {} {} sx: {}", sc.getOperation(), sc.getKey(), (sc.getChildren() != null ? sc.getChildren().size() : 0));

             sc.getChildren().forEach(r -> {
                log.info("ch {} ", r.getOperation());
                r.getChildren().forEach(rr -> {
                    log.info("ch ::=>  {} {} [{}] {}", r.getOperation() , rr.getKey(),rr.getOperation(),rr.getValue());
                });
             });

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    public Node<SearchCriteria> createNode(SearchCriteria orgUnidad) {
        return new Node<SearchCriteria>(orgUnidad.getId(), orgUnidad);
    }

    String jsonval =  "{\r\n" + //
            "\t\"operation\": \"ROOT\",\r\n" + //
            "\t\"children\": [\r\n" + //
            "\t\t{\r\n" + //
            "\t\t\t\"operation\": \"AND\",\r\n" + //
            "\t\t\t\"children\": [\r\n" + //
            "\t\t\t\t{\r\n" + //
            "\t\t\t\t\t\"key\": \"nombre\",\r\n" + //
            "\t\t\t\t\t\"operation\": \":\",\r\n" + //
            "\t\t\t\t\t\"value\": 1\r\n" + //
            "\t\t\t\t},\r\n" + //
            "\t\t\t\t{\r\n" + //
            "\t\t\t\t\t\"key\": \"descr\",\r\n" + //
            "\t\t\t\t\t\"operation\": \":\",\r\n" + //
            "\t\t\t\t\t\"value\": \"aasdf\"\r\n" + //
            "\t\t\t\t}\r\n" + //
            "\t\t\t]\r\n" + //
            "\t\t},\r\n" + //
            "\t\t{\r\n" + //
            "\t\t\t\"operation\": \"OR\",\r\n" + //
            "\t\t\t\"children\": [\r\n" + //
            "\t\t\t\t{\r\n" + //
            "\t\t\t\t\t\"key\": \"edad\",\r\n" + //
            "\t\t\t\t\t\"operation\": \":\",\r\n" + //
            "\t\t\t\t\t\"value\": 2\r\n" + //
            "\t\t\t\t},\r\n" + //
            "\t\t\t\t{\r\n" + //
            "\t\t\t\t\t\"key\": \"edad\",\r\n" + //
            "\t\t\t\t\t\"operation\": \":\",\r\n" + //
            "\t\t\t\t\t\"value\": 3\r\n" + //
            "\t\t\t\t},\r\n" + //
            "\t\t\t\t{\r\n" + //
            "\t\t\t\t\t\"key\": \"edad\",\r\n" + //
            "\t\t\t\t\t\"operation\": \":\",\r\n" + //
            "\t\t\t\t\t\"value\": 5\r\n" + //
            "\t\t\t\t}\t\t\t\t\r\n" + //
            "\t\t\t]\r\n" + //
            "\t\t}\r\n" + //
            "\t]\r\n" + //
            "}";
}

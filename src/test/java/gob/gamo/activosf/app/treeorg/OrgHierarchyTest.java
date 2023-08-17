package gob.gamo.activosf.app.treeorg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import gob.gamo.activosf.app.domain.OrgUnidad;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrgHierarchyTest {

    OrgUnidad u1 = OrgUnidad.builder().id_unidad(1000).domicilio("DDDDDDD").build();
    OrgUnidad u2 = OrgUnidad.builder().id_unidad(2).id_unidad_padre(1000).domicilio("asdf asd2").build();
    OrgUnidad u3 = OrgUnidad.builder().id_unidad(300).id_unidad_padre(1000).domicilio("afsred").build();
    OrgUnidad u5 = OrgUnidad.builder().id_unidad(5).id_unidad_padre(2).domicilio("5").build();
    OrgUnidad u6 = OrgUnidad.builder().id_unidad(6000).id_unidad_padre(300).domicilio("6 adscadfdf").build();
    OrgUnidad u7 = OrgUnidad.builder().id_unidad(7).id_unidad_padre(5).domicilio("7").build();
    OrgUnidad u8 = OrgUnidad.builder().id_unidad(888).id_unidad_padre(5).domicilio("8").build();
    OrgUnidad u9 = OrgUnidad.builder().id_unidad(9).id_unidad_padre(888).domicilio("9").build();
    OrgUnidad u10 = OrgUnidad.builder().id_unidad(10).id_unidad_padre(300).domicilio("10").build();
    OrgUnidad u11 = OrgUnidad.builder().id_unidad(11).id_unidad_padre(888).domicilio("11").build();
    OrgUnidad u12 = OrgUnidad.builder().id_unidad(12).id_unidad_padre(11).domicilio("12 asdwerqe").build();
    OrgUnidad u15 = OrgUnidad.builder().id_unidad(100).id_unidad_padre(7).domicilio("15 asdwerqe").build();
    OrgUnidad u16 = OrgUnidad.builder().id_unidad(101).id_unidad_padre(12).domicilio("101 asdwerqe").build();
    OrgUnidad u17 = OrgUnidad.builder().id_unidad(102).id_unidad_padre(101).domicilio("101 asdwerqe").build();
    OrgUnidad u18 = OrgUnidad.builder().id_unidad(105).id_unidad_padre(102).domicilio("105 asdwerqe").build();

    @Test
    void tree() {
        try {
            log.info("********* iniciooooooooooooooo ************ root {} ", u1.getId_unidad());
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            org.hireOwner(new Node<OrgUnidad>(u1.getId_unidad(), u1));

            org.hireEmployee(createNode(u2), u2.getId_unidad_padre());
            org.hireEmployee(createNode(u3), u3.getId_unidad_padre());
            org.hireEmployee(createNode(u5), u5.getId_unidad_padre());
            org.hireEmployee(createNode(u6), u6.getId_unidad_padre());
            org.hireEmployee(createNode(u7), u7.getId_unidad_padre());
            org.hireEmployee(createNode(u8), u8.getId_unidad_padre());
            org.hireEmployee(createNode(u9), u9.getId_unidad_padre());
            org.hireEmployee(createNode(u10), u10.getId_unidad_padre());
            org.hireEmployee(createNode(u11), u11.getId_unidad_padre());
            org.hireEmployee(createNode(u12), u12.getId_unidad_padre());
            org.hireEmployee(createNode(u15), u15.getId_unidad_padre());
            org.hireEmployee(createNode(u16), u16.getId_unidad_padre());
            org.hireEmployee(createNode(u17), u17.getId_unidad_padre());
            // org.hireEmployee(createNode(u12), u12.getId_unidad_padre());

            Node<OrgUnidad> rootid = org.returnRoot();
            org.nivls(rootid).stream().forEach(l -> {
                log.info("" + l);
            });

            rootid.nivls().stream().forEach(l -> {
                log.info("" + l);
            });
            Node<OrgUnidad> root = org.returnRoot();

            log.info("toStringNode 1 -> {} root: {} sRoot {} level: {} ", org.toStringNode(1000), root.nodeID(),
                    org.size(), org.maxLevel());

            Node<OrgUnidad> empSearch = org.searchNode(u3.getId_unidad());
            LinkedList<Node<OrgUnidad>> result = empSearch.childs();
            result.forEach(r -> {
                log.info("Chil {}", r.nodeID());
            });
            log.info("Replace =======> size {}", org.size());
            org.hireEmployee(createNode(u18), u18.getId_unidad_padre());
            Node<OrgUnidad> unold = org.searchNode(u5.getId_unidad());
            log.info("Replace ch> {} lvl: {}", unold.nodeID(), unold.maxLevel());
            // org.fireEmployee(u2.getId_unidad(), u3.getId_unidad());
            // org.fireWithNew(u2.getId_unidad(), createNode(u18),
            // u18.getId_unidad_padre());
            // org.hireEmployee(createNode(u2), u2.getId_unidad_padre());

            org.nivls(unold).stream().forEach(l -> {
                log.info("" + l);
            });

            unold.nivls().stream().forEach(l -> {
                log.info("" + l);
            });
            log.info("Replace neww =======> {} sRoot{} level{} ", unold.nodeID(), org.size(), org.maxLevel());
        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }

    @Test
    void treeArray() {
        try {
            log.info("********* iniciooooooooooooooo ************ ");
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            List<OrgUnidad> list = Arrays.asList(u1, u10, u11, u15, u2, u3, u5, u17, u16, u7, u8, u9, u12);

            Integer idpadre = 5;

            Optional<OrgUnidad> oupadre = list.stream()
                    .filter(r -> idpadre != null && r.getId_unidad().compareTo(idpadre) == 0).findFirst();
            if (oupadre.isPresent()) {
                List<OrgUnidad> listR = search(list, idpadre);
                org.hireOwner(new Node<OrgUnidad>(oupadre.get().getId_unidad(), oupadre.get()));

                listR.forEach(r -> {
                    log.info("OU: {} padre: {}", r.getId_unidad(), r.getId_unidad_padre());
                    if (r.getId_unidad().compareTo(idpadre) != 0) {
                        org.hireEmployee(createNode(r), r.getId_unidad_padre());
                    }
                });
            }

            Node<OrgUnidad> root = org.returnRoot();
            root.nivls().stream().forEach(l -> {
                log.info("" + l);
            });

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }

    @Test
    void treeArrayUp() {
        try {
            log.info("********* iniciooooooooooooooo ************ ");
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            List<OrgUnidad> list = Arrays.asList(u1, u10, u11, u15, u2, u3, u5, u17, u16, u7, u8, u9, u12);

            Integer idpadre = 102;

            Optional<OrgUnidad> oupadre = list.stream()
                    .filter(r -> idpadre != null && r.getId_unidad().compareTo(idpadre) == 0).findFirst();

            if (oupadre.isPresent()) {
                List<OrgUnidad> listR = searchUp(list, oupadre.get().getId_unidad_padre());
                org.hireOwner(new Node<OrgUnidad>(oupadre.get().getId_unidad(), oupadre.get()));

                listR.forEach(r -> {
                    log.info("OU: {} padre: {}", r.getId_unidad(), r.getId_unidad_padre());
                    if (r.getId_unidad().compareTo(idpadre) != 0) {
                        //org.hireEmployee(createNode(r), r.getId_unidad_padre());
                    }
                });
            }

            Node<OrgUnidad> root = org.returnRoot();
            root.nivls().stream().forEach(l -> {
                log.info("" + l);
            });

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }

    public List<OrgUnidad> search(List<OrgUnidad> list, Integer idpadre) {
        List<OrgUnidad> result = new ArrayList<>();

        result = list.stream().filter(r -> idpadre != null && r.getId_unidad().compareTo(idpadre) == 0)
                .collect(Collectors.toList());

        List<OrgUnidad> conPadre = list.stream().filter(r -> idpadre != null && r.getId_unidad_padre() != null
                && r.getId_unidad_padre().compareTo(idpadre) == 0).collect(Collectors.toList());

        for (OrgUnidad orgUnidad : conPadre) {
            if (orgUnidad.getId_unidad() > 0) {
                List<OrgUnidad> conPadreAux = search(list, orgUnidad.getId_unidad());
                result.addAll(conPadreAux);
            }
        }
        return result;
    }

    public List<OrgUnidad> searchUp(List<OrgUnidad> list, Integer idpadre) {
        List<OrgUnidad> result = new ArrayList<>();

        result = list.stream().filter(r -> idpadre != null && r.getId_unidad().compareTo(idpadre) == 0)
                .collect(Collectors.toList());
        return result;
    }

    public Node<OrgUnidad> createNode(OrgUnidad orgUnidad) {
        return new Node<OrgUnidad>(orgUnidad.getId_unidad(), orgUnidad);
    }
}

package gob.gamo.activosf.app.treeorg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.services.UnidadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrgHierarchyTest {

    OrgUnidad u1 = OrgUnidad.builder().idUnidad(1000).domicilio("DDDDDDD").build();
    OrgUnidad u2 = OrgUnidad.builder().idUnidad(2).idUnidadPadre(1000).domicilio("asdf asd2").build();
    OrgUnidad u3 = OrgUnidad.builder().idUnidad(300).idUnidadPadre(1000).domicilio("afsred").build();
    OrgUnidad u5 = OrgUnidad.builder().idUnidad(5).idUnidadPadre(2).domicilio("5").build();
    OrgUnidad u6 = OrgUnidad.builder().idUnidad(6000).idUnidadPadre(300).domicilio("6 adscadfdf").build();
    OrgUnidad u7 = OrgUnidad.builder().idUnidad(7).idUnidadPadre(5).domicilio("7").build();
    OrgUnidad u8 = OrgUnidad.builder().idUnidad(888).idUnidadPadre(5).domicilio("8").build();
    OrgUnidad u9 = OrgUnidad.builder().idUnidad(9).idUnidadPadre(888).domicilio("9").build();
    OrgUnidad u10 = OrgUnidad.builder().idUnidad(10).idUnidadPadre(300).domicilio("10").build();
    OrgUnidad u11 = OrgUnidad.builder().idUnidad(11).idUnidadPadre(888).domicilio("11").build();
    OrgUnidad u12 = OrgUnidad.builder().idUnidad(12).idUnidadPadre(11).domicilio("12 asdwerqe").build();
    OrgUnidad u15 = OrgUnidad.builder().idUnidad(100).idUnidadPadre(7).domicilio("15 asdwerqe").build();
    OrgUnidad u16 = OrgUnidad.builder().idUnidad(101).idUnidadPadre(12).domicilio("101 asdwerqe").build();
    OrgUnidad u17 = OrgUnidad.builder().idUnidad(102).idUnidadPadre(101).domicilio("101 asdwerqe").build();
    OrgUnidad u18 = OrgUnidad.builder().idUnidad(105).idUnidadPadre(102).domicilio("105 asdwerqe").build();

    @Test
    void tree() {
        try {
            log.info("********* iniciooooooooooooooo ************ root {} ", u1.getIdUnidad());
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            org.hireOwner(new Node<OrgUnidad>(u1.getIdUnidad(), u1));

            org.addNewChild(createNode(u2), u2.getIdUnidadPadre());
            org.addNewChild(createNode(u3), u3.getIdUnidadPadre());
            org.addNewChild(createNode(u5), u5.getIdUnidadPadre());
            org.addNewChild(createNode(u6), u6.getIdUnidadPadre());
            org.addNewChild(createNode(u7), u7.getIdUnidadPadre());
            org.addNewChild(createNode(u8), u8.getIdUnidadPadre());
            org.addNewChild(createNode(u9), u9.getIdUnidadPadre());
            org.addNewChild(createNode(u10), u10.getIdUnidadPadre());
            org.addNewChild(createNode(u11), u11.getIdUnidadPadre());
            org.addNewChild(createNode(u12), u12.getIdUnidadPadre());
            org.addNewChild(createNode(u15), u15.getIdUnidadPadre());
            org.addNewChild(createNode(u16), u16.getIdUnidadPadre());
            org.addNewChild(createNode(u17), u17.getIdUnidadPadre());
            // org.hireEmployee(createNode(u12), u12.getIdUnidadPadre());

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

            Node<OrgUnidad> empSearch = org.searchNode(u3.getIdUnidad());
            LinkedList<Node<OrgUnidad>> result = empSearch.childs();
            result.forEach(r -> {
                log.info("Chil {}", r.nodeID());
            });
            log.info("Replace =======> size {}", org.size());
            org.addNewChild(createNode(u18), u18.getIdUnidadPadre());
            Node<OrgUnidad> unold = org.searchNode(u5.getIdUnidad());
            log.info("Replace ch> {} lvl: {}", unold.nodeID(), unold.maxLevel());
            org.replaceWithOtherNode(u2.getIdUnidad(), u3.getIdUnidad());
            //org.fireWithNew(u2.getIdUnidad(), createNode(u18), u18.getIdUnidadPadre());
            // org.hireEmployee(createNode(u2), u2.getIdUnidadPadre());

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
                    .filter(r -> idpadre != null && r.getIdUnidad().compareTo(idpadre) == 0).findFirst();
            if (oupadre.isPresent()) {
                List<OrgUnidad> listR = search(list, idpadre);

                org.hireOwner(new Node<OrgUnidad>(oupadre.get().getIdUnidad(), oupadre.get()));

                listR.forEach(r -> {
                    log.info("OU: {} padre: {}", r.getIdUnidad(), r.getIdUnidadPadre());
                    if (r.getIdUnidad().compareTo(idpadre) != 0) {
                        org.addNewChild(createNode(r), r.getIdUnidadPadre());
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
                    .filter(r -> idpadre != null && r.getIdUnidad().compareTo(idpadre) == 0).findFirst();

            if (oupadre.isPresent()) {
                List<OrgUnidad> listR = searchUp(list, oupadre.get().getIdUnidadPadre());
                org.hireOwner(new Node<OrgUnidad>(oupadre.get().getIdUnidad(), oupadre.get()));

                listR.forEach(r -> {
                    log.info("OU: {} padre: {}", r.getIdUnidad(), r.getIdUnidadPadre());
                    if (r.getIdUnidad().compareTo(idpadre) != 0) {
                        // org.hireEmployee(createNode(r), r.getIdUnidadPadre());
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
    void searchNodesTest() {
        try {
            log.info("********* iniciooooooooooooooo searchNodesTest ************ ");
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            List<OrgUnidad> list = Arrays.asList(u1, u10, u11, u15, u2, u3, u5, u17, u16, u7, u8, u9, u12);

            Integer idpadre = 102;

            Optional<OrgUnidad> oupadre = list.stream()
                    .filter(r -> idpadre != null && r.getIdUnidad().compareTo(idpadre) == 0).findFirst();

            if (oupadre.isPresent()) {
                List<OrgUnidad> listDown = search(list, idpadre);
                log.info("nodes down {} ", listDown.size());
                listDown.forEach(r -> {
                    log.info("Down OU: {} padre: {}", r.getIdUnidad(), r.getIdUnidadPadre());
                });

                List<OrgUnidad> listUp = searchUp(list, oupadre.get().getIdUnidadPadre());
                log.info("nodes Up {} ", listUp.size());
                listUp.forEach(r -> {
                    log.info("Up OU: {} padre: {}", r.getIdUnidad(), r.getIdUnidadPadre());
                });

                OrgUnidad root = searchRoot(list, idpadre);
                log.info("node Root {} ", root.getIdUnidad());
            }

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }

    @Test
    void genTreeNodesTest() {
        try {
            log.info("********* iniciooooooooooooooo genTreeNodesTest ************ ");
            OrgHierarchy<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            u2.setIdUnidadPadre(null);
            List<OrgUnidad> list = Arrays.asList(u10, u11, u15, u2, u3, u1, u5, u17, u16, u7, u8, u9, u12);

            Integer idpadre = 102;
            OrgUnidad root = searchRoot(list, idpadre);
            if (root == null) {
                throw new DataException("Registro raiz inexistente");
            }
            log.info("Root lista {}", root.getIdUnidad());
            org.hireOwner(new Node<OrgUnidad>(root.getIdUnidad(), root));

            List<OrgUnidad> hijosRoot = hijos(list, root.getIdUnidad());

            for (OrgUnidad ou : hijosRoot) {
                org.addNewChild(createNode(ou), ou.getIdUnidadPadre());

               log.info("Root OU: {} padre: {} ", ou.getIdUnidad(), ou.getIdUnidadPadre());
                addHijos(list, org, ou.getIdUnidad());
            }

            Node<OrgUnidad> rootid = org.returnRoot();
            log.info("Root nodes {}", rootid.nodeID());

            org.nivls(rootid).stream().forEach(l -> {
                log.info("" + l);
            });

            log.info("sixe org {} vs sixe list {}", org.size(), list.size());
            //org.fireEmployee(u2.getIdUnidad(), u3.getIdUnidad());
            //org = UnidadService.generarOrgTree(list, u12);
            org.updateParent(12, 5);
            u12.setIdUnidadPadre(5);
            org = (OrgHierarchy<OrgUnidad>) UnidadService.generarOrgTree(list, u12);

            rootid = org.returnRoot();
            log.info("Root Changed nodes {}", rootid.nodeID());

            org.nivls(rootid).stream().forEach(l -> {
                log.info("" + l);
            });

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }

    @Test
    void updateTest() {
        try {
            log.info("********* iniciooooooooooooooo genTreeNodesTest ************ ");
            OrgHierarchyInterface<OrgUnidad> org = new OrgHierarchy<OrgUnidad>();
            List<OrgUnidad> list = Arrays.asList(u10, u11, u15, u2, u3, u1, u5, u17, u16, u7, u8, u9, u12);

            Integer idpadre = 102;

        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);
        }
    }
    public void addHijos(List<OrgUnidad> list, OrgHierarchyInterface<OrgUnidad> org, Integer idUnidad) {
        List<OrgUnidad> hijosRoot = hijos(list, idUnidad);
        for (OrgUnidad ou : hijosRoot) {
            log.info("  Add OU: {} hijo: {} hijos: [{}]", idUnidad, ou.getIdUnidad(), hijosRoot.size());            
            org.addNewChild(createNode(ou), ou.getIdUnidadPadre());
            addHijos(list, org, ou.getIdUnidad());
        }
    }

    public List<OrgUnidad> hijos(List<OrgUnidad> list, Integer idpadre) {
        return list.stream().filter(r -> idpadre != null && r.getIdUnidadPadre() != null
                && r.getIdUnidadPadre().compareTo(idpadre) == 0).collect(Collectors.toList());
    }

    public List<OrgUnidad> search(List<OrgUnidad> list, Integer idpadre) {

        List<OrgUnidad> result = list.stream().filter(r -> idpadre != null && r.getIdUnidad().compareTo(idpadre) == 0)
                .collect(Collectors.toList());

        List<OrgUnidad> conPadre = list.stream().filter(r -> idpadre != null && r.getIdUnidadPadre() != null
                && r.getIdUnidadPadre().compareTo(idpadre) == 0).collect(Collectors.toList());

        for (OrgUnidad orgUnidad : conPadre) {
            if (orgUnidad.getIdUnidad() > 0) {
                List<OrgUnidad> conPadreAux = search(list, orgUnidad.getIdUnidad());
                result.addAll(conPadreAux);
            }
        }
        return result;
    }

    public List<OrgUnidad> searchUp(List<OrgUnidad> list, Integer idpadre) {
        List<OrgUnidad> result = new ArrayList<>();

        result = list.stream().filter(r -> idpadre != null && r.getIdUnidad().compareTo(idpadre) == 0)
                .collect(Collectors.toList());
        return result;
    }

    public OrgUnidad searchRoot(List<OrgUnidad> list, Integer idpadre) {
        if (idpadre == null) {
            return null;
        }

        Optional<OrgUnidad> oupadre = list.stream()
                .filter(r -> r.getIdUnidad() != null && r.getIdUnidad().compareTo(idpadre) == 0).findFirst();
        if (!oupadre.isPresent()) {
            throw new DataException("Registro Padre inexistente " + idpadre);
        }

        if (oupadre.get().getIdUnidadPadre() != null) {
            log.info("hijo {} -> padre {}", idpadre, oupadre.get().getIdUnidadPadre());
            OrgUnidad found = searchRoot(list, oupadre.get().getIdUnidadPadre());
            return found;
        }

        return oupadre.get();
    }

    public Node<OrgUnidad> createNode(OrgUnidad orgUnidad) {
        return new Node<OrgUnidad>(orgUnidad.getIdUnidad(), orgUnidad);
    }
}

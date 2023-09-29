package gob.gamo.activosf.app.treeorg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.OrgUnidad;

@Slf4j
public class Tester {
    public static void main(String[] args) {
        int N = 10;
        OrgHierarchyInterface org = new OrgHierarchy();
        log.info("--Initialized an Empty Tree--************");
        // isEmpty()
        log.info("Is Tree Empty- " + org.isEmpty());

        // hireowner
        try {
            org.hireOwner(1);
            log.info("Hired Owner successfully with id = 1");
        } catch (NotEmptyException e) {
            log.info("Error: " + e.getMessage());
        }

        // isEmpty()
        log.info("Is Tree Empty- " + org.isEmpty() + "");

        // catch notempty exception
        log.info("--Trying to hire owner with id = 2--");
        try {
            org.hireOwner(2);
        } catch (NotEmptyException e) {
            log.info("Error 2: " + e.getMessage());
        }

        /*
         * Tree-
         * 1
         * / | \
         * 3 2 12
         * / \ | |
         * 8 4 7 10
         * / \
         * 16 5
         */

        // insert employees
        try {
            org.addNewChild(3, 1);
            // log.info("Hired employee successfully with id = 3");
            org.addNewChild(2, 1);
            // log.info("Hired employee successfully with id = 2");
            org.addNewChild(12, 1);
            // log.info("Hired employee successfully with id = 12");
            org.addNewChild(8, 3);
            // log.info("Hired employee successfully with id = 8");
            org.addNewChild(4, 3);
            // log.info("Hired employee successfully with id = 4");
            org.addNewChild(7, 2);
            // log.info("Hired employee successfully with id = 7");
            org.addNewChild(10, 12);
            // log.info("Hired employee successfully with id = 10");
            org.addNewChild(16, 8);
            // log.info("Hired employee successfully with id = 16");
            org.addNewChild(5, 8);
            // log.info("Hired employee successfully with id = 5");
            org.addNewChild(55, 4);
            org.addNewChild(25, 4);
            org.addNewChild(18, 4);
            org.addNewChild(21, 10);
            org.addNewChild(11, 10);
        } catch (IllegalIDException e) {
            log.info(e.getMessage());
        }

        // print tree
        Node nn = org.searchNode(1);

        List<String> strl = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            LinkedList<Node<OrgUnidad>> e1 = org.getbylevel(nn, i);
            String sn2 = e1.stream()
                    .map(nodo -> nodo.getKey() + " ["
                            + nodo.childArray.stream()
                                    .map(no -> no.getKey() + "")
                                    .collect(Collectors.joining(";")) + "]")
                    .collect(Collectors.joining("   "));
            strl.add(sn2);
        }
        strl.stream().forEach(l -> {
            log.info(l);
        });
        /* LinkedList<Node<OrgUnidad>> e1 = org.getbylevel(nn, 2);
        String sn2 = e1.stream().map(nodo -> nodo.getKey() + " [" +
                nodo.childArray.stream().map(no -> no.getKey() + "").collect(Collectors.joining(";")) + "]")
                .collect(Collectors.joining(";"));
        LinkedList<Node<OrgUnidad>> e3 = org.getbylevel(nn, 3);
        String sn3 = e3.stream().map(nodo -> nodo.getKey() + " [" +
                nodo.childArray.stream().map(no -> no.getKey() + "").collect(Collectors.joining(";")) + "]")
                .collect(Collectors.joining(";"));

        log.info("** update --------------> ");
        log.info(sn2);
        log.info(sn3); */

        int lcb0 = org.lowestCommonBoss(4, 16);
        log.info("** Root ::> " + nn.getKey() + " " + nn.hasParent() + " comun (4,16) " + lcb0);
        nn.print(5);
        // org.fireEmployee(12, 3);
        // log.info("** update --------------> " );
        // nn.print(5);
        int size1 = org.size();
        log.info("New Organization Size: " + size1);
        try {
            log.info("toStringNode 1 = " + org.toStringNode(1)); // "1,2 3 12,4 7 8 10,5 16"
        } catch (IllegalIDException e) {
            log.info(e.getMessage());
        }

        // check size
        if (size1 != N) {
            log.info("FAIL");
        } else {
            log.info("PASS");
        }
        // check level

        try {
            log.info("What is the level of employee 5?");
            int l = org.level(5);
            log.info("Level = " + l);
            if (l != 4) {
                log.info("FAIL");
            } else {
                log.info("PASS");
            }
        } catch (IllegalIDException e) {
            log.info(e.getMessage());
        }

        // check fireEmployee
        try {
            log.info("--Trying to fire employee with id = 7--");
            org.deleteNode(7);
            size1 = org.size();
            log.info("--Employee fired successfully--");
            log.info("Size: " + size1);
            log.info("Updated Organization toStringNode 1 = " + org.toStringNode(1));

            if (size1 != N - 1) {
                log.info("FAIL");
            } else {
                log.info("PASS");
            }
        } catch (IllegalIDException e) {
            log.info(e.getMessage());
        }

        // check fireEmployee(id, Manageid)
        Node<OrgUnidad> rootid = org.searchNode(1);

        try {
            log.info("-- fire employee with id = 8 and make id = 4 new boss of its children--");
            rootid.print(5);
            log.info("Despuesta post 8 con c =======>");
            org.replaceWithOtherNode(8, 4);
            rootid.print(5);
            size1 = org.size();

            log.info("--Employee fired successfully--");
            log.info("Size: " + size1);
            log.info("New Tree toStringNode 1: " + org.toStringNode(1));
            log.info("Subtree rooted toStringNode 3 " + org.toStringNode(3));
            // check if boss is changed after deletion
            int boss1 = org.boss(5);
            if (boss1 != 4) {
                log.info("FAIL");
            } else {
                log.info("--Employee 3 is made new boss successfully--");
                log.info("PASS");
            }
        } catch (IllegalIDException e) {
            log.info("Employee does not exist");
        }
        org.nivls(rootid).stream().forEach(l -> {
            log.info("" + l);
        });
        // check lowest common boss-
        try {
            log.info("--Find lowest common boss of employees 3 and 10--");
            rootid.print(5);
            int lcb = org.lowestCommonBoss(3, 10);
            log.info("Lowest common boss = " + lcb);
            if (lcb != 1) {
                log.info("FAIL");
            } else {
                log.info("PASS");
            }
            nn = org.getRoot();
            log.info("**toStringNode root: " + nn.getKey() + " -> " + org.toStringNode(-1));
            nn.print(5);
        } catch (IllegalIDException e) {
            log.info("Employee does not exist");
        }
    }
}

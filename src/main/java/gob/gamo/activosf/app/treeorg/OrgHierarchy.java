package gob.gamo.activosf.app.treeorg;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrgHierarchy<T> extends Tree<T> implements OrgHierarchyInterface<T> {
    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        } else return false;
    }

    public int size() {
        return size(this.root);
    }

    public int level(int id) throws IllegalIDException {
        Node<T> requiredEmployee = search(id);
        if (requiredEmployee == null) {
            throw new IllegalIDException("No Employee with given ID : " + id + "!");
        } else return requiredEmployee.getLevel();
    }

    public int maxLevel() {
        Node<T> nRoot = returnRoot();
        int nlvl = 1;

        for (Node<T> child : nRoot.childArray) {
            int lvl = maxLevel(1, child);
            if (lvl > nlvl) nlvl = lvl;
        }

        return nlvl;
    }

    public int maxLevel(int lvl, Node<T> n) throws IllegalIDException {
        int nlvl = lvl;
        for (Node<T> child : n.childArray) {
            int lch = maxLevel(lvl + 1, child);
            if (lch >= nlvl) nlvl = lch;
        }
        if (lvl == nlvl) nlvl = lvl + 1;
        // log.info("malevel {} -> {}",lvl, nlvl);
        return nlvl;
    }

    public Node<T> searchNode(int id) throws IllegalIDException {
        Node<T> requiredEmployee = search(id);
        if (requiredEmployee == null) {
            throw new IllegalIDException("No Employee with given ID : " + id + "!");
        } else return requiredEmployee;
    }

    public void hireOwner(int id) throws NotEmptyException {
        Node<T> subNode = new Node<T>(id);
        hireOwner(subNode);
    }

    public void hireOwner(Node<T> node) throws NotEmptyException {
        if (this.root != null) {
            throw new NotEmptyException("The organisation already has Employees (& hence an owner)!");
        } else {
            try {
                insertKey(node);
            } catch (IllegalKeyException e) {
                throw new NotEmptyException(
                        "The organisation already has an Employee with given ID : " + node.getKey() + "!");
            }
        }
        // log.info("root {}", root.nodeID());
    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException {
        Node<T> subNode = new Node<T>(id);
        hireEmployee(subNode, bossid);
    }

    public void hireEmployee(Node<T> subNode, int bossid) throws IllegalIDException {
        int id = subNode.getKey();

        // log.info("insertando {} con boss {}", id, bossid);
        Node<T> theBoss = search(bossid);
        if (theBoss == null) {
            throw new IllegalIDException("No Employee with given Boss ID : " + bossid + "!");
        }
        try {
            insertKey(subNode);
        } catch (IllegalKeyException e) {
            throw new IllegalIDException("The organisation already has an Employee with given ID : " + id + "!");
        }
        Node<T> theNewEmployee = search(id);
        theNewEmployee.setLevel(theBoss.getLevel() + 1);
        theNewEmployee.parent = theBoss;
        theBoss.getChildArray().add(theNewEmployee);
        // log.info("level {} newid: {} :> {}", maxLevel(), id, theNewEmployee.nodeID());
    }

    public void fireEmployee(int id) throws IllegalIDException {
        Node<T> theEmployee = search(id);

        if (theEmployee == null) {
            throw new IllegalIDException("No Employee with given ID : " + id + " to be deleted!");
        }
        if (theEmployee.getChildArray().size() != 0) {
            throw new IllegalIDException("Could NOT delete the Employee with given ID : " + id
                    + " as it has some Employees! Please provide manageid for new boss of Employees under him");
        }
        Node<T> hisBoss = theEmployee.parent;
        if (hisBoss != null) {
            hisBoss.childArray.remove(theEmployee);
        }
        theEmployee.parent = null;
        deleteKey(id); // No need to handle the case when Employee ID doesn't exist here, as already
        // handled above
    }

    public void fireEmployee(int id, int manageid) throws IllegalIDException {
        Node<T> theOldManager = search(id);
        if (theOldManager == null) {
            throw new IllegalIDException("No Employee with given ID : " + id + " to be deleted!");
        }
        Node<T> theNewManager = search(manageid);
        if (theNewManager == null) {
            throw new IllegalIDException("No Employee with given ID : " + manageid + " for new manager!");
        }
        if (theNewManager.getLevel() != theOldManager.getLevel()) {
            throw new IllegalIDException(
                    "The employees with IDs " + id + " & " + manageid + " are NOT on the same level!");
        }
        LinkedList<Node<T>> employeesUnderOldManager = theOldManager.getChildArray();
        LinkedList<Node<T>> employessUnderNewManager = theNewManager.getChildArray();

        for (Node<T> n : employeesUnderOldManager) {
            employessUnderNewManager.add(n);
            n.parent = theNewManager;
        }

        theOldManager.getChildArray().removeFirst();
        Node<T> hisBoss = theOldManager.parent;
        if (hisBoss != null) {
            hisBoss.childArray.remove(theOldManager);
        }
        theOldManager.parent = null;

        deleteKey(id);
    }

    public void fireWithNew(int id, Node<T> manage, int bossid) throws IllegalIDException {
        hireEmployee(manage, bossid);
        fireEmployee(id, manage.getKey());
    }

    public int boss(int id) throws IllegalIDException {
        Node<T> theEmployee = search(id);
        if (theEmployee == null) {
            throw new IllegalIDException("No Employee with given ID : " + id + "!");
        }
        Node<T> theBoss = theEmployee.parent;
        if (theBoss == null) {
            return -1;
        }
        return theBoss.getKey();
    }

    public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
        Node<T> pointer1 = search(id1);
        Node<T> pointer2 = search(id2);
        if (pointer1 == null || pointer2 == null) {
            throw new IllegalIDException(
                    "Please check of both employee IDs " + id1 + " and " + id2 + " are present in the organisation!");
        }
        Node<T> commonpointer = null;
        while (pointer1 != null) {
            pointer2 = search(id2);
            while (pointer2 != null) {
                if (pointer1 == pointer2) {
                    commonpointer = pointer1;
                    break;
                } else {
                    pointer2 = pointer2.parent;
                }
            }
            if (commonpointer != null) {
                break;
            } else pointer1 = pointer1.parent;
        }
        return commonpointer.getKey();
    }

    public String toStringNode(int id) throws IllegalIDException {
        Node<T> rootid = search(id < 0 ? root.getKey() : id);
        if (rootid == null) {
            throw new IllegalIDException("No Employee with given Boss ID : " + id + "!");
        }
        String res = "";
        Queue<T> queue = new Queue<T>();
        queue.enqueue(rootid);
        int currentLevel = 0;
        LinkedList<Node<T>> levelList = new LinkedList<Node<T>>();
        while (!queue.isEmpty()) {
            Node<T> varNode = queue.dequeue();

            if (varNode != null) {

                if (varNode.getLevel() != currentLevel) { // If next level
                    for (Node<T> n : levelList) {
                        res = res + n.getKey() + " ";
                    }
                    levelList = new LinkedList<Node<T>>();
                    currentLevel += 1;
                    currentLevel = varNode.getLevel();
                }
                levelList.add(varNode);
                LinkedList<Node<T>> employessUnderIt = varNode.getChildArray();

                if (employessUnderIt.size() == 0) {
                    continue;
                } else {
                    for (Node<T> n : employessUnderIt) {
                        queue.enqueue(n);
                    }
                }
            }
        }
        // The last level
        for (Node<T> n : levelList) {
            res = res + n.getKey() + " ";
        }
        return res;
    }

    public LinkedHashMap<Integer, Node<T>> recuperarSwfCampos(Node<T> root) {
        LinkedHashMap<Integer, Node<T>> treeCampos = new LinkedHashMap<Integer, Node<T>>();
        recTreeCampos(root, treeCampos);
        return treeCampos;
    }

    public void recTreeCampos(Node<T> node, LinkedHashMap<Integer, Node<T>> treeCampos) {
        treeCampos.put(node.getKey(), node);
        node.getChildArray().forEach(ch -> {
            recTreeCampos(ch, treeCampos);
        });
    }

    public LinkedList<Node<T>> getbylevel(Node<T> root, int level) {
        LinkedHashMap<Integer, Node<T>> mapa = recuperarSwfCampos(root);
        LinkedList<Node<T>> employessUnderIt = new LinkedList<Node<T>>();
        for (Entry<Integer, Node<T>> entry : mapa.entrySet()) {
            if (entry.getValue().getLevel() == level) {
                employessUnderIt.add(entry.getValue());
            }
        }
        return employessUnderIt;
    }

    public List<String> nivls(Node<T> root) {
        List<String> strl = new ArrayList<>();
        int lvl = maxLevel();
        for (int i = 1; i <= lvl; i++) {
            LinkedList<Node<T>> e1 = getbylevel(root, i);
            String sn2 = e1.stream()
                    .map(nodo -> nodo.getKey() + " ["
                            + nodo.childArray.stream()
                                    .map(no -> no.getKey() + "")
                                    .collect(Collectors.joining(";")) + "]")
                    .collect(Collectors.joining("   "));
            if (e1.size() > 0) strl.add(sn2);
        }
        return strl;
    }

    public Node<T> returnRoot() {
        return returnRoot(null);
    }

    public Node<T> returnRoot(Node<T> node) {
        if (isEmpty()) {
            return null;
        }

        node = node == null ? getRoot() : node;
        if (node.hasParent()) {
            Node<T> parent = returnRoot(node.getParent());
            return parent;
        }
        return node;
    }
}

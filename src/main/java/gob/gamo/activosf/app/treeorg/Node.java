package gob.gamo.activosf.app.treeorg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Node<T> {
    private int key;
    private int level;
    private int height;

    private T value;
    private T valueNxt;

    public Node<T> nodeLeft;
    public Node<T> nodeRight;
    Node<T> parent;
    LinkedList<Node<T>> childArray;

    public Node(int data) {
        key = data;
        nodeLeft = null;
        nodeRight = null;
        height = 0;
        level = 1;
        childArray = new LinkedList<Node<T>>();
        parent = null;
    }

    public Node(int data, T t) {
        key = data;
        value = t;
        nodeLeft = null;
        nodeRight = null;
        height = 0; // Just keep it 0 here (Avltree implementation)
        level = 1; // Just keep it 1 here (Generic Tree implementation)
        childArray = new LinkedList<Node<T>>(); // (Generic Tree implementation)
        parent = null; // (Generic Tree implementation)
    }

    /*
     * public Node(T value) {
     * this.value = value;
     * childArray = new LinkedList<>();
     * }
     */
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public LinkedList<Node<T>> getChildArray() {
        return childArray;
    }

    public void setChildArray(LinkedList<Node<T>> childArray) {
        this.childArray = childArray;
    }

    public Node<T> addChild0(Node<T> child) {
        if (!child.hasParent()) {
            childArray.add(child);
            child.setParent(this);
        } else {
            Node<T> p = addChild0(child.getParent());
        }
        return this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = (T) value;
    }

    /*
     * public T getValueCrr() {
     * return valueCrr;
     * }
     * 
     * public void setValueCrr(T valueCrr) {
     * this.valueCrr = (T) valueCrr;
     * }
     */

    public T getValueNxt0() {
        return valueNxt;
    }

    public void setValueNxt0(T valueNxt) {
        this.valueNxt = (T) valueNxt;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasCurrent() {
        return value != null;
    }

    public boolean hasNext0() {
        return valueNxt != null;
    }

    public LinkedList<Node<T>> childs() {
        if (childArray.size() > 0) {
            LinkedList<Node<T>> result = new LinkedList<Node<T>>();
            for (Node<T> child : childArray) {
                LinkedList<Node<T>> stbRes = childs(child);
                result.addAll(stbRes);
            }
            return result;
        } else {
            return new LinkedList<Node<T>>();
        }
    }

    LinkedList<Node<T>> childs(Node<T> n) {
        if (n == null) {
            return new LinkedList<Node<T>>();
        }

        LinkedList<Node<T>> result = new LinkedList<Node<T>>();
        if (n.childArray.size() > 0) {
            result.add(n);
            for (Node<T> child : n.childArray) {
                LinkedList<Node<T>> stbRes = childs(child);
                result.addAll(stbRes);
            }
        } else {
            result.add(n);
        }

        return result;
    }

    Map<Integer, LinkedList<Node<T>>> treeCampos = new HashMap<Integer, LinkedList<Node<T>>>();

    public Map<Integer, LinkedList<Node<T>>> getTreeCampos() {
        return treeCampos;
    }

    public List<String> nivls() {
        List<String> strl = new ArrayList<>();
        int lvl = maxLevel();
        
        for (Entry<Integer, LinkedList<Node<T>>> e : treeCampos.entrySet()) {
            int i = e.getKey();
            LinkedList<Node<T>> e1 = treeCampos.get(i);
            String sn2 = e1.stream().map(nodo -> nodo.getKey() + " [" +
                    nodo.childArray.stream().map(no -> no.getKey() + "").collect(Collectors.joining(";")) + "]")
                    .collect(Collectors.joining("   "));
            if (e1.size() > 0)
                strl.add(sn2);
        }
        return strl;
    }

    public int maxLevel() {
        int nlvl = 1;
        treeCampos = new HashMap<Integer, LinkedList<Node<T>>>();

        treeCampos.put(0, new LinkedList<Node<T>>());
        treeCampos.get(0).add(this);

        for (Node<T> child : childArray) {
            int lvl = maxLevel(1, child);
            if (lvl > nlvl)
                nlvl = lvl;
            /*
             * if (!treeCampos.containsKey(lvl)) {
             * treeCampos.put(lvl, new LinkedList<Node<T>>());
             * }
             * treeCampos.get(lvl).add(child);
             */
            // treeCampos.put(lvl, childArray);
        }

        return nlvl;
    }

    int maxLevel(int lvl, Node<T> n) throws IllegalIDException {
        int nlvl = lvl;

        if (!treeCampos.containsKey(n.getLevel())) {
            treeCampos.put(n.getLevel(), new LinkedList<Node<T>>());
        }
        treeCampos.get(n.getLevel()).add(n);

        for (Node<T> child : n.childArray) {
            int lch = maxLevel(lvl + 1, child);
            if (lch > nlvl) {
                nlvl = lch;
            }
/*             if (!treeCampos.containsKey(child.getLevel())) {
                treeCampos.put(child.getLevel(), new LinkedList<Node<T>>());
            }
            treeCampos.get(n.getLevel()).add(n); */
        }

        if (lvl == nlvl) {
            nlvl = lvl + 1;
        }

        return nlvl;
    }

    public void print(Integer lvl) {
        String corr = StringUtils.leftPad("", lvl, "" + level);
        String inf = (nodeID());
        log.info(corr + " " + inf);

        // }
        childArray.forEach(ch -> {
            ch.print(lvl + 2);
        });
    }

    public String nodeID() {
        // String corr = StringUtils.leftPad("", lvl, "" + level);
        String padre = hasParent() ? parent.getKey() + "" : "SP";
        String s = childArray.stream().map(nn -> nn.getKey() + "").collect(Collectors.joining(";"));
        String inf = padre + "->" + key + "\t\t(" + (nodeLeft != null ? nodeLeft.key : "SV ") + " [" + key + "] "
                + (nodeRight != null ? nodeRight.key : " SV)") + " -> [" + s + "]";
        return padre + "->" + key + "(" + height + ")" + " -> [" + s + "]" + " LVL: " + level;
    }
    /*
     * public String parentID() {
     * if (swfMencampos != null && swfMencampos instanceof SwfMencampos)
     * return hasParent() && parent.getSwfMencampos() != null
     * ? ((SwfMencampos) parent.getSwfMencampos()).toStringID()
     * : " SIN PADRE";
     * else
     * return null;
     * }
     * 
     * public String currentID() {
     * if (swfMencampos != null && swfMencampos instanceof SwfMencampos)
     * return hasCurrent() ? ((SwfMencampos) swfMencamposCrr).toStringID() : "";
     * else
     * return null;
     * }
     * 
     * public String nodoID() {
     * if (swfMencampos != null && swfMencampos instanceof SwfMencampos)
     * return swfMencampos != null ? ((SwfMencampos) swfMencampos).toStringID() :
     * "";
     * else
     * return null;
     * }
     */

}

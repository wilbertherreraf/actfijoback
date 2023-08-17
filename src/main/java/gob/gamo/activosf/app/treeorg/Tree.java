package gob.gamo.activosf.app.treeorg;

import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tree<T> {
    Node<T> root;

    public Tree() {
        // root = new Node<T>();
    }

    public Tree(Node<T> root) {
        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public void addChild(Node<T> parent, Node<T> child) {
        if (parent == null || child == null)
            return;
        parent.getChildArray().add(child);
        child.setParent(parent);
    }

    protected int size(Node<T> root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.nodeLeft) + size(root.nodeRight);
    }

    private Node<T> searchRecursive(Node<T> root, int key) {
        if (root == null || key == root.getKey()) {
            return root;
        } else if (key < root.getKey()) {
            return searchRecursive(root.nodeLeft, key);
        } else {
            return searchRecursive(root.nodeRight, key);
        }
    }

    Node<T> search(int key) {
        return searchRecursive(root, key);
    }

    int balanceFactor(Node<T> N) {
        return N == null ? 0 : getHeight(N.nodeLeft) - getHeight(N.nodeRight);
    }

    // Helper functions
    private int getHeight(Node<T> node) {
        return node == null ? 0 : node.getHeight();
    }

    private Node<T> clockwise(Node<T> node) {
        Node<T> var = node.nodeLeft;
        node.nodeLeft = var.nodeRight;
        var.nodeRight = node;
        node.setHeight(1 + Math.max(getHeight(node.nodeLeft), getHeight(node.nodeRight)));
        var.setHeight(1 + Math.max(getHeight(var.nodeLeft), getHeight(var.nodeRight)));
        return var;
    }

    private Node<T> anticlockwise(Node<T> node) {
        Node<T> var;
        var = node;
        node = var.nodeRight;
        var.nodeRight = node.nodeLeft;
        node.nodeLeft = var;
        var.setHeight(1 + Math.max(getHeight(var.nodeLeft), getHeight(var.nodeRight)));
        node.setHeight(1 + Math.max(getHeight(node.nodeLeft), getHeight(node.nodeRight)));
        return node;
    }

    private Node<T> insertRecursive(Node<T> nodeRoot, Node<T> newE) throws IllegalKeyException {
        int key = newE.getKey();

        if (nodeRoot == null) {
            //root = new Node<T>(key);
            // return root;
            return newE;
        }
        
        if (nodeRoot.getKey() > key)
            nodeRoot.nodeLeft = insertRecursive(nodeRoot.nodeLeft, newE);
        else if (nodeRoot.getKey() < key)
            nodeRoot.nodeRight = insertRecursive(nodeRoot.nodeRight, newE);
        else
            throw new IllegalKeyException();

        nodeRoot.setHeight(1 + Math.max(getHeight(nodeRoot.nodeLeft), getHeight(nodeRoot.nodeRight)));
        int balance = balanceFactor(nodeRoot);
        //if (getHeight(nodeRoot.nodeLeft) - getHeight(nodeRoot.nodeRight) > 1) {
        /* if (balance > 1) {            
            if (key < nodeRoot.nodeLeft.getKey()) {
                //nodeRoot = clockwise(nodeRoot);
                return clockwise(nodeRoot);
            } else {
                nodeRoot.nodeLeft = anticlockwise(nodeRoot.nodeLeft);
                nodeRoot = clockwise(nodeRoot);
            }
        } else if (getHeight(nodeRoot.nodeRight) - getHeight(nodeRoot.nodeLeft) > 1) {
            if (key > nodeRoot.nodeRight.getKey()) {
                nodeRoot = anticlockwise(nodeRoot);
            } else {
                nodeRoot.nodeRight = clockwise(nodeRoot.nodeRight);
                nodeRoot = anticlockwise(nodeRoot);
            }
        } */

        if (balance > 1 && newE.getKey() < nodeRoot.nodeLeft.getKey())
            return clockwise(nodeRoot);
 
        // Right Right Case
        if (balance < -1 && newE.getKey() > nodeRoot.nodeRight.getKey())
            return anticlockwise(nodeRoot);
 
        // Left Right Case
        if (balance > 1 && newE.getKey() > nodeRoot.nodeLeft.getKey()) {
            nodeRoot.nodeLeft = anticlockwise(nodeRoot.nodeLeft);
            return clockwise(nodeRoot);
        }
 
        // Right Left Case
        if (balance < -1 && newE.getKey() < nodeRoot.nodeRight.getKey()) {
            nodeRoot.nodeRight = clockwise(nodeRoot.nodeRight);
            return anticlockwise(nodeRoot);
        }        
        return nodeRoot;
    }

    void insertKey(Node<T> newElem) throws IllegalKeyException {
        root = insertRecursive(root, newElem);
    }

    void insertKey(int key) throws IllegalKeyException {
        Node<T> newElem = new Node<T>(key);
        insertKey(newElem);
    }

    private Node<T> deleteRecursive(Node<T> root, int key) {
        if (root == null) {
            return root;
        }

        if (root.getKey() > key) {
            root.nodeLeft = deleteRecursive(root.nodeLeft, key);
        } else if (root.getKey() < key) {
            root.nodeRight = deleteRecursive(root.nodeRight, key);
        } else {
            if (root.nodeLeft == null && root.nodeRight == null) {
                root = null;
                return root;
            } else if (root.nodeLeft == null) {
                return root.nodeRight;
            } else if (root.nodeRight == null) {
                return root.nodeLeft;
            } else {
                Node<T> var = root.nodeRight;
                while (var.nodeLeft != null) {
                    var = var.nodeLeft;
                }

                root.setKey(var.getKey());

                root.childArray = new LinkedList<Node<T>>(); ////
                LinkedList<Node<T>> rootChildren = root.childArray; ////
                LinkedList<Node<T>> varChildren = var.childArray; ////
                for (Node<T> n : varChildren) {
                    rootChildren.add(n); ////
                }

                if (var.childArray.size() > 0)
                    var.childArray.removeFirst();
                root.parent = var.parent; ////
                if (var.parent != null) { ////
                    var.parent.childArray.remove(var); ////
                    var.parent.childArray.push(root);
                } ////
                root.setLevel(var.getLevel()); ////
                root.nodeRight = deleteRecursive(root.nodeRight, root.getKey());
            }
        }

        root.setHeight(1 + Math.max(getHeight(root.nodeLeft), getHeight(root.nodeRight)));

        if (getHeight(root.nodeLeft) - getHeight(root.nodeRight) > 1) {
            if (getHeight(root.nodeLeft.nodeLeft) >= getHeight(root.nodeLeft.nodeRight)) {
                root = clockwise(root);
            } else {
                root.nodeLeft = anticlockwise(root.nodeLeft);
                root = clockwise(root);
            }
        } else if (getHeight(root.nodeRight) - getHeight(root.nodeLeft) > 1) { 
            if (getHeight(root.nodeRight.nodeRight) >= getHeight(root.nodeRight.nodeLeft)) { 
                root = anticlockwise(root);
            } else { // Right - Left case
                root.nodeRight = clockwise(root.nodeRight);
                root = anticlockwise(root);
            }
        }
        return root;
    }

    void deleteKey(int key) {
        root = deleteRecursive(root, key);
    }
}

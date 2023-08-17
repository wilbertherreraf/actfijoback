package gob.gamo.activosf.app.treeorg;

public class Queue<T> {

    static class QueueNode<T> {
        private Node<T> data = null;
        private QueueNode<T> next = null;

        // Constructors
        private QueueNode(Node<T> o) {
            this.data = o;
        }

        private QueueNode() {
        }

        // Accessor methods
        private Node<T> getElement() {
            return data;
        }

        private QueueNode<T> getNext() {
            return next;
        }

        // Modifier methods
        // public void setElement(Node<T>new_data){this.data = new_data;}
        private void setNext(QueueNode<T> new_next) {
            this.next = new_next;
        }
    }

    // Head Node: Simialr to Top Pointer
    private QueueNode<T> head;
    private QueueNode<T> tail;

    // Constructor
    public Queue() {
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int getSize() {
        if (this.head == null) {
            return 0;
        }
        QueueNode<T> var = this.head;
        int count = 0;
        while (var != null) {
            var = var.getNext();
            count += 1;
        }
        return count;
    }

    public void enqueue(Node<T> new_data){
        QueueNode<T> new_node = new QueueNode<T>(new_data);
        if (this.head==null) {
            this.head = new_node;
            this.tail = new_node;
        } else {
            this.tail.setNext(new_node);
            this.tail = new_node;
        }
    }

    public Node<T> dequeue() {
        if (this.head == null) {
            return null;
        } else if (this.head == this.tail) {
            Node<T> popped = this.head.data;
            this.head = null;
            this.tail = null;
            return popped;
        } else {
            Node<T> popped = this.head.data; // this.head.getElement();
            this.head = this.head.next; // this.head.getNext();
            return popped;
        }
    }

    public Node<T> getFront() {
        if (this.head == null) {
            return null;
        } else {
            return this.head.getElement();
        }
    }

    public Node<T> getRear() {
        if (this.head == null) {
            return null;
        } else {
            return this.tail.getElement();
        }
    }
}

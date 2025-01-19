package struct.impl;

public class Node<T> {
    public T value;
    public Node<T> next;
    public Node<T> previous;
    public Node(T value) {
        this.value = value;
    }
}


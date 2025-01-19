package struct;

public interface LinkedList<T> extends Iterable<T> {
    int size();
    boolean isEmpty();
    void insertFirst(T value);
    void insertLast(T value);
    T removeFirst();
    T removeLast();
    boolean contains(T value);
    boolean remove(T value);
}

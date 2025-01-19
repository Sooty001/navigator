package struct;

import java.util.Comparator;

public interface List<T> extends Iterable<T> {
    int size();
    int capacity();
    T get(int index);
    T getLast();
    T getFirst();
    boolean isEmpty();
    boolean add(T o);
    boolean addAll(Iterable<T> list);
    boolean remove(int index);
    boolean contains(T o);
    List<T> subList(int startIndex, int endIndex);
    void sort(Comparator<T> comparator);
}


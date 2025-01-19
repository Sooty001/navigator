package struct;

import struct.impl.LinkedListImpl;

import java.util.Iterator;

public interface Set<T> extends Iterable<T> {
    boolean add(T key);
    LinkedListImpl<T> findBucketsByKey(T key);
    boolean remove(T key);
    boolean contains(T key);
    boolean isEmpty();
    int size();
    int capacity();
}

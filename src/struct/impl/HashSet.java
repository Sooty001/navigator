package struct.impl;

import struct.Set;

import java.util.Iterator;

public class HashSet<T> implements Set<T> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75d;
    private int size;
    private int capacity;
    private LinkedListImpl<T>[] buckets;

    public HashSet() {
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
        createBuckets(capacity);
    }

    public HashSet(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        createBuckets(capacity);
    }

    @Override
    public boolean add(T key) {
        growIfNeeded();
        int bucketIndex = hashCode(key);
        LinkedListImpl<T> bucket = buckets[bucketIndex];
        if (!bucket.contains(key)) {
            bucket.insertLast(key);
            size++;
            return true;
        }
        return false;
    }

    @Override
    public LinkedListImpl<T> findBucketsByKey(T key) {
        int bucketIndex = hashCode(key);
        return buckets[bucketIndex];
    }

    @Override
    public boolean remove(T key) {
        int bucketIndex = hashCode(key);
        LinkedListImpl<T> bucket = buckets[bucketIndex];
        if(bucket.remove(key)) {
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(T key) {
        int bucketIndex = hashCode(key);
        LinkedListImpl<T> bucket = buckets[bucketIndex];
        return bucket.contains(key);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    private void createBuckets(int capacity) {
        buckets = new LinkedListImpl[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedListImpl<>();
        }
    }

    private int hashCode(T key) {
        return Math.abs(key.hashCode() % capacity);
    }

    private void growIfNeeded() {
        if ((double) size() / capacity() > LOAD_FACTOR) {
            grow();
        }
    }

    private void grow() {
        LinkedListImpl<T>[] oldBuckets = buckets;
        capacity = capacity * 2;
        buckets = new LinkedListImpl[capacity];

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedListImpl<T>();
        }

        for (LinkedListImpl<T> bucket : oldBuckets) {
            for (T value : bucket) {
                int bucketIndex = hashCode(value);
                buckets[bucketIndex].insertLast(value);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    private class HashSetIterator implements Iterator<T> {
        private int bucketIndex = 0;
        private Iterator<T> currentBucketIterator = buckets[0].iterator();

        @Override
        public boolean hasNext() {
            while (bucketIndex < capacity) {
                if (currentBucketIterator.hasNext()) {
                    return true;
                }
                bucketIndex++;
                if (bucketIndex < capacity) {
                    currentBucketIterator = buckets[bucketIndex].iterator();
                }
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            return currentBucketIterator.next();
        }
    }
}

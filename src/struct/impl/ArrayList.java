package struct.impl;

import struct.List;

import java.util.Comparator;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 16; private static final double LOAD_FACTOR = 0.80d;
    private T[] list;
    private int size;
    private int capacity;

    public ArrayList(){
        this.capacity = INITIAL_CAPACITY;
        list = (T[]) new Object[capacity];
        this.size = 0;
    }

    public ArrayList(int capacity){
        this.capacity = capacity;
        list = (T[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return list[index];
    }

    @Override
    public T getLast() {
        return size == 0 ? null : list[size - 1];
    }

    @Override
    public T getFirst() {
        return size == 0 ? null : list[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T o) {
        growIfNeeded();
        if(o == null)
            return false;
        list[size] = o;
        size++;
        return true;
    }

    @Override
    public boolean addAll(Iterable<T> list) {
        boolean isChanged = false;
        for (T temp : list) {
            if (add(temp)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    private void growIfNeeded() {
        if ((double) size() / capacity() > LOAD_FACTOR) {
            grow();
        }
    }

    private void grow() {
        capacity *= 2;
        T[] newList = (T[]) new Object[capacity];
        System.arraycopy(list, 0, newList, 0, size);
        list = newList;
    }

    @Override
    public boolean remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        list[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public boolean contains(T str) {
        for (int i = 0; i < size; i++) {
            if (str != null && str.equals(list[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<T> subList(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex > size || startIndex > endIndex || startIndex == endIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<T> subList = new ArrayList<>(endIndex - startIndex);
        for (int i = startIndex; i < endIndex; i++) {
            subList.add(list[i]);
        }
        return subList;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        quickSort(list, comparator, 0, size - 1);
    }

    private void quickSort(T[] array, Comparator<T> comparator, int firstIndex, int endIndex) {
        if (firstIndex < endIndex) {
            int pi = partition(array, comparator, firstIndex, endIndex);
            quickSort(array, comparator, firstIndex, pi - 1);
            quickSort(array, comparator, pi + 1, endIndex);
        }
    }

    private int partition(T[] array, Comparator<T> comparator, int firstIndex, int endIndex) {
        T target = array[endIndex];
        int i = firstIndex - 1;
        for (int j = firstIndex; j < endIndex; j++) {
            if (comparator.compare(array[j], target) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, endIndex);
        return i + 1;
    }

    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                return list[count++];
            }
        };
    }
}

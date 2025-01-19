package struct.impl;

import struct.LinkedList;

import java.util.Iterator;

public class LinkedListImpl<T> implements LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public LinkedListImpl(){
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void insertFirst(T value) {
        Node<T> link = new Node<T>(value);
        if(isEmpty()){
            last = link;
        }
        else{
            first.previous = link;
        }
        link.next = first;
        first = link;
        size++;
    }

    @Override
    public void insertLast(T value) {
        Node<T> link = new Node<T>(value);
        if(isEmpty()){
            first = link;
        }
        else{
            last.next = link;
            link.previous = last;
        }
        last = link;
        size++;
    }

    @Override
    public T removeFirst() {
        Node<T> temp = first;
        if(first.next == null){
            last = null;
        }
        else
            first.next.previous = null;
        first = first.next;
        size--;
        return temp.value;
    }

    @Override
    public T removeLast() {
        Node<T> temp = last;
        if(last.previous == null){
            first = null;
        }
        else
            last.previous.next = null;
        last = last.previous;
        size--;
        return temp.value;
    }

    @Override
    public boolean contains(T value) {
        Node<T> temp = first;
        while (temp != null) {
            if (value.equals(temp.value)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public boolean remove(T value) {
        Node<T> temp = first;
        while (temp != null) {
            if (value.equals(temp.value)) {
                if (temp.previous != null) {
                    temp.previous.next = temp.next;
                } else {
                    first = temp.next;
                }
                if (temp.next != null) {
                    temp.next.previous = temp.previous;
                } else {
                    last = temp.previous;
                }
                size--;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
package org.tec.datos1.linkeddb;

public class DoubleNode<T> {
    private DoubleNode next = null;
    private DoubleNode prev = null;
    private T value;

    public DoubleNode(T value, DoubleNode<T> prev){
        this.value = value;
        this.prev = prev;
    }

    public DoubleNode getNext() {
        return next;
    }

    public DoubleNode getPrev() {
        return prev;
    }

    public T getValue() {
        return value;
    }

    public void setNext(DoubleNode next) {
        this.next = next;
    }

    public void setPrev(DoubleNode prev) {
        this.prev = prev;
    }

    public void setValue(T value) {
        this.value = value;
    }

}

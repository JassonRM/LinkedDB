package org.tec.datos1.linkeddb;

/**
 * Esta clase denota un nodo para una lista doblemente enlazada
 * @param <T> Tipo de dato a almacenar
 */
public class DoubleNode<T> {
    private DoubleNode next = null;
    private DoubleNode prev = null;
    private T value;

    /**
     * Constructor para asignar el valor y el nodo anterior
     * @param value valor que va a tomar el nodo
     * @param prev referencia al nodo anterior
     */
    public DoubleNode(T value, DoubleNode<T> prev){
        this.value = value;
        this.prev = prev;
    }

    /**
     * @return el nodo siguiente
     */
    public DoubleNode getNext() {
        return next;
    }

    /**
     * @return el nodo previo
     */
    public DoubleNode getPrev() {
        return prev;
    }

    /**
     * @return el valor del nodo
     */
    public T getValue() {
        return value;
    }

    /**
     * @param next establece el siguiente nodo
     */
    public void setNext(DoubleNode next) {
        this.next = next;
    }

    /**
     * @param prev establece el nodo previo
     */
    public void setPrev(DoubleNode prev) {
        this.prev = prev;
    }

    /**
     * @param value establece el valor del nodo
     */
    public void setValue(T value) {
        this.value = value;
    }

}

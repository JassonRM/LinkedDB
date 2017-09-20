package org.tec.datos1.linkeddb;

import java.util.Iterator;

/**
 * Esta clase denota una lista doblemente enlazada
 * @param <T> Tipo de objetos que va a contener la lista
 */
public class DoubleList<T extends JSONprinter> implements Iterable<T>{
    private DoubleNode head = null;
    private DoubleNode tail = null;

    /**
     * Anade un nuevo valor a la lista
     * @param value valor a anadir
     */
    public void append(T value){
        if (this.head == null){
            this.head = new DoubleNode<T>(value, null);
            this.tail = this.head;
        }
        else{
            DoubleNode nodo = this.head;
            while (nodo.getNext() != null){
                nodo = nodo.getNext();
            }

            nodo.setNext(new DoubleNode<T>(value, nodo));
            this.tail = nodo.getNext();

        }
    }

    /**
     * Determina si un valor existe
     * @param value valor a analizar
     * @return un boolean indicando si el valor existe
     */
    public boolean exists(String value) {
        DoubleNode<T> temp = this.head;
        while (temp != null){
            if (temp.getValue().toString() == value){
                return true;
            }
            else{
                temp = temp.getNext();
            }
        }
        return false;
    }

    /**
     * Busca un valor en la lista
     * @param value valor a buscar
     * @return la instancia
     */
    public T search(String value) {
        DoubleNode<T> temp = this.head;
        while (temp != null){
            if (temp.getValue().toString() == value){
                return temp.getValue();
            }
            else{
                temp = temp.getNext();
            }
        }
        return null;
    }

    /**
     * Calcula el largo de la lista
     * @return un entero con el largo de la lista
     */
    public int length(){
        DoubleNode<T> temp = this.head;
        int length = 0;
        while (temp != null){
            length++;
            temp = temp.getNext();
        }
    return length;
    }

    /**
     * Crea un array para ser convertido a documento JSON
     * @return array con los valores del metodo .toJSON() de cada uno de sus elementos
     */
    public Object[] toJSONArray(){
        Object[] array = new Object[this.length()];
        DoubleNode<T> temp = this.head;
        int count = 0;
        while (temp != null){
            array[count] = temp.getValue().toJSON();
            count++;
            temp = temp.getNext();
        }
    return array;
    }

    /**
     * Crea un iterador para recorrer la lista
     * @return Devuelve la instancia del iterador
     */
    @Override
    public Iterator iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            DoubleNode<T> node;

            /**
             * @return Devuelve un boolean que dice si existe el elemento siguiente
             */
            @Override
            public boolean hasNext() {
                if(node == null && head != null || node != null && node.getNext() != null){
                    return true;
                } else {
                    return false;
                }
            }

            /**
             * @return Devuelve el elemento siguiente
             */
            @Override
            public T next() {
                if (node == null && head != null){
                    node = head;
                    return node.getValue();
                }
                else{
                    if(node.getNext() != null){
                        node = node.getNext();
                        return node.getValue();
                    }else {
                        return null;
                    }
                }
            }
        };
        return iterator;
    }

    /**
     * @return el primer elemento de la lista
     */
    public DoubleNode getHead() {
        return head;
    }

    /**
     * @param head establece el primer elementode la lista
     */
    public void setHead(DoubleNode head) {
        this.head = head;
    }

    /**
     * @return el ultimo elemento de la lista
     */
    public DoubleNode getTail() {
        return tail;
    }

    /**
     * @param tail establece el ultimo elemento de la lista
     */
    public void setTail(DoubleNode tail) {
        this.tail = tail;
    }
}

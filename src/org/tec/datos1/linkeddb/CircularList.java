package org.tec.datos1.linkeddb;

import java.util.Iterator;

/**
 * Esta clase denota una lista circular doblemente enlazada
 * @param <T> Tipo de valor a almacenar
 */
public class CircularList<T extends JSONprinter> implements Iterable<T>{
    private DoubleNode head = null;

    /**
     * Anade un valor al final de la lista
     * @param value Valor que va a ser almacenado
     */
    public void append(T value){
        if (this.head == null){
            this.head = new DoubleNode(value, null);
            this.head.setPrev(this.head);
            this.head.setNext(this.head);
        }
        else{
            DoubleNode<T> tail = this.head.getPrev();
            DoubleNode<T> nuevo = new DoubleNode<>(value, tail);
            tail.setNext(nuevo);
            nuevo.setNext(this.head);
            this.head.setPrev(nuevo);
        }
    }

    /**
     * Busca un valor en la lista mediante su metodo .toString() y devuelve una referencia a la instancia
     * @param value Valor utilizado como parametro de busqueda
     * @return valor encontrado
     */
    public T search(String value) {
        DoubleNode<T> temp = this.head;
        if (temp != null){
            do {
                if (temp.getValue().toString().equals(value)) {
                    return temp.getValue();
                } else {
                    temp = temp.getNext();
                }
            }while(temp != this.head);
        }
        else {
            return null;
        }
        return null;
    }

    /**
     * Calcula la longitud de la lista
     * @return Longitut de la lista
     */
    public int length(){
        DoubleNode<T> temp = this.head;
        int length = 0;
        if (temp != null) {
            do {
                length++;
                temp = temp.getNext();
            } while (temp != this.head);
        }
        return length;
    }

    /**
     * Crea un array para ser convertido a JSON y llama el metodo toJSON() de cada uno de los valores almacenados
     * @return Devuelve el array creado
     */
    public Object[] toJSONArray() {
        Object[] array = new Object[this.length()];
        DoubleNode<T> temp = this.head;
        int count = 0;
        if (temp != null){
            do {
                array[count] = temp.getValue().toJSON();
                count++;
                temp = temp.getNext();
            } while (temp != this.head);
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
                if(node == null && head != null || node != null && node.getNext() != head){
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
                    if(node.getNext() != head){
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
     * @return Devuelve el primer elemento de la lista
     */
    public DoubleNode getHead() {
        return head;
    }

    /**
     * @param head Establece el primer nodo en la lista
     */
    public void setHead(DoubleNode head) {
        this.head = head;
    }
}

package org.tec.datos1.linkeddb;

public class DoubleList<T>{
    private DoubleNode head = null;
    private DoubleNode tail = null;

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

//    public void print(){
//        if (this.head == null){
//            System.out.println("Lista vacia");
//        }
//        else{
//            DoubleNode nodo = this.head;
//            while (nodo != null){
//                nodo.getValue();
//                nodo = nodo.getNext();
//            }
//        }
//    }




}

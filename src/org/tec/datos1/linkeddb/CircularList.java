package org.tec.datos1.linkeddb;

public class CircularList<T>{
    private DoubleNode head = null;

    public void append(T value){
        if (this.head == null){
            this.head = new DoubleNode(value, this.head);
        }
        else{
            DoubleNode<T> tail = this.head.getPrev();
            DoubleNode<T> nuevo = new DoubleNode<>(value, tail);
            tail.setNext(nuevo);
            nuevo.setNext(this.head);
        }
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

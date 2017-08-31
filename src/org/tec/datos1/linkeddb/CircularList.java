package org.tec.datos1.linkeddb;

public class CircularList<T>{
    private DoubleNode head = null;

    public void append(T value){
        if (this.head == null){
            this.head = new DoubleNode(value, null);
        }
        else{
            DoubleNode inicio = this.head;
            DoubleNode nodo = this.head;
            while (nodo.getNext() != inicio){
                nodo = nodo.getNext();
            }

            nodo.setNext(new DoubleNode(value, nodo));
            this.head.setPrev(nodo.getNext());

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

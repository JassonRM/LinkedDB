package org.tec.datos1.linkeddb;

public class DoubleList<T extends JSONprinter>{
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

    public int length(){
        DoubleNode<T> temp = this.head;
        int length = 0;
        while (temp != null){
            length++;
            temp = temp.getNext();
        }
    return length;
    }

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

    public DoubleNode getHead() {
        return head;
    }

    public void setHead(DoubleNode head) {
        this.head = head;
    }

    public DoubleNode getTail() {
        return tail;
    }

    public void setTail(DoubleNode tail) {
        this.tail = tail;
    }
}

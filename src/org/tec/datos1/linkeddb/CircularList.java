package org.tec.datos1.linkeddb;

public class CircularList<T extends JSONprinter>{
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

    public DoubleNode getHead() {
        return head;
    }

    public void setHead(DoubleNode head) {
        this.head = head;
    }
}

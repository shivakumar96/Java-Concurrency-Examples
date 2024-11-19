package org.example.additional;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/*
This Atomic Reference is pessimistic approach towards synchronization
It used CAS operation, which is a special instruction supported at the hardware level.
CAS instruction is atomic at the H?W level. SO No additional explicit locking mechanism is needed

This is approach is work better when the conflicts are rare.
 */


class Node{
    int val;
    Node next;
    Node(int val, Node next){
        this.val =val;
        this.next =next;
    }
}

class AtomicReferenceStack{
    AtomicReference<Node> stackHead ;
    AtomicReferenceStack(){
        stackHead = new AtomicReference<Node>(null);
    }
    public void push(int val){
        Node curr;
        Node newNode;
        do{
            curr = stackHead.get();
            newNode = new Node(val,curr);
        }while (!stackHead.compareAndSet(curr,newNode));
    }

    public int pop(){
        Node curr;
        Node newNode;
        do{
            curr = stackHead.get();
            if(curr==null)
                return -1;
            newNode = curr.next;
        }while (!stackHead.compareAndSet(curr,newNode));

        return  curr.val;
    }
}

public class AtomicReferenceExample {

    public static void main(String[] args) {

        AtomicReferenceStack stack = new AtomicReferenceStack();
        ExecutorService service = Executors.newFixedThreadPool(5);

        class Push implements Runnable {
            AtomicReferenceStack stack;
            int val;
            Push(int val, AtomicReferenceStack stack){
                this.val=val;
                this.stack= stack;
            }
            @Override
            public void run() {
                stack.push(val);
            }
        }

        class Pop implements Runnable {
            AtomicReferenceStack stack;
            Pop(AtomicReferenceStack stack){
                this.stack = stack;
            }

            @Override
            public void run() {
                int val = stack.pop();
                System.out.println(System.currentTimeMillis()+" : "+val);
            }
        }

        for(int i=0;i<100;i++){
            Runnable task = (i%2==0)?new Pop(stack) : new Push(i,stack);
            service.submit(task);
        }

        service.shutdown();

    }

}

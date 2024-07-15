package org.example.implemantion.basic;

public class Visibility {

    //importance of volatile
    volatile boolean flag1 =false;
    boolean flag2 = false;

    Runnable setter = new Runnable() {
        @Override
        public void run() {
            //while(!flag1){}
            while(!flag2){}
            System.out.println("Consumed");
        }
    };

    Runnable consumer = new Runnable() {
        @Override
        public void run() {
            for(long i=0; i<100000000;i++){}
            flag2 = true;
            flag1 = true;
            System.out.println("Producer - Done");
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Visibility v = new Visibility();
        Thread t1 = new Thread(v.setter,"setter");
        Thread t2 = new Thread(v.consumer,"consumer");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }


}

package org.example.implementation.basic;

public class Synchronized {
    int num = 0;
    Runnable r1 = ()->{
        for(int i=0;i<10000;i++){
            synchronized (this){
                num++;
            }
        }
    };
    Runnable r2 = ()->{
        for(int i=0;i<10000;i++){
            synchronized (this){
                num++;
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Synchronized s = new Synchronized();
        Thread t1 = new Thread(s.r1);
        Thread t2  = new Thread(s.r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(s.num);
    }
}

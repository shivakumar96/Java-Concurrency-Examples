package org.example.implemantion.basic;

class MyThread extends Thread {

    public void run(){
        System.out.println("Thread Hello");
    }
}

class MyThread2 implements Runnable {

    @Override
    public void run(){
        System.out.println("Thread Hello2");
    }
}


public class BasicThread {

    public static void main(String[] args) throws InterruptedException {

        new MyThread().start();
        MyThread2 thread = new MyThread2();
        new Thread(thread).start();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 3");
            }
        };
        new Thread(runnable).start();

        Runnable runnable1 = ()->{
            System.out.println("Thread 4");
        };
        new Thread(runnable1).start();


    }
}

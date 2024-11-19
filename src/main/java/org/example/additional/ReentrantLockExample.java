package org.example.additional;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockExample {

    // The name ReentrantLock comes from the behavior
    // i.e it allows to lock any number of times by the same object: caveat should be unlocked same number of times
    // in the recursion we are locking recursively
    public static ReentrantLock lock = new ReentrantLock();

    public static void recursion(int n){
        if(n==0)
            return;
        lock.lock();
            recursion(n-1);
            System.out.println(Thread.currentThread()+" "+n);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
    }

    // better approach is that use try-catch or try
    public static void recursion2(int n){
        if(n==0)
            return;
        lock.lock();
        try {
            recursion2(n - 1);
            System.out.println(Thread.currentThread()+" "+n);
        } finally {
            lock.unlock();
        }
    }

    public static Runnable task = new Runnable() {
        @Override
        public void run() {
            recursion(5);
        }
    };

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}

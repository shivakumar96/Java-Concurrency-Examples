package org.example.additional;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public  static int val =0;
    public static void main(String[] args) throws InterruptedException {
        // allows multiple reads and only one write simultaneously
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        // Only write lock allows condition

        Runnable read = ()->{
            readLock.lock();
            System.out.println(val);
            readLock.unlock();
        };
        Runnable write = new Runnable() {
            @Override
            public void run() {
                writeLock.lock();
                val++;
                writeLock.unlock();
            }
        };

        Thread t1 = new Thread(read);
        Thread t2 = new Thread(read);
        Thread t3 = new Thread(read);
        Thread t4 = new Thread(write);
        Thread t5 = new Thread(write);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

    }
}

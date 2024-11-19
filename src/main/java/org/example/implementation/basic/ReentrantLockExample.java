package org.example.implementation.basic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Single Producer and Consumer
 */
public class ReentrantLockExample {
    Lock lock ;
    int p =0;
    Condition readCond, writeCond;
    Queue<Integer> queue;
    int MAX_SIZE;

    public ReentrantLockExample(int n){
        MAX_SIZE = n;
        lock = new ReentrantLock(); // unfair lock
        // lock = new ReentrantLock(true); fair lock
        readCond = lock.newCondition();
        writeCond = lock.newCondition();
        queue = new LinkedList<>();
    }


    //other methods include
    // lock.tryLock() -> returns boolean
    // lock.tryLock(time, timeunit) -> wait for specified time to acquire the lock
    // tryLock doesn't honer the fairness
    Runnable producer = ()->{
        while (true) {
            lock.lock();
            try {
                while (queue.size() >= MAX_SIZE)
                    writeCond.await();
                queue.add(p++);
                readCond.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    };



    //slow consumer
    Runnable consumer = ()->{
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty())
                    readCond.await();
                System.out.println("Consumed: "+queue.poll());
                writeCond.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample example = new ReentrantLockExample(1);
        Thread producerThread = new Thread(example.producer);
        Thread consumerThread = new Thread(example.consumer);
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
    }

}

package org.example.implementation.basic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Multiple producer and single consumer example Using semaphore
 */
public class SemaphoresExample {
    private Semaphore readSem ;
    private Semaphore writeSem ;
    private Semaphore acquireAccess ;
    private Queue<String> queue;

    public SemaphoresExample( int n){
        readSem = new Semaphore(0);
        writeSem = new Semaphore(5);
        acquireAccess = new Semaphore(1);
        queue = new LinkedList<>();
    }

    Runnable produce = ()->{
        int i = 0;
        while (true){
            try {
                writeSem.acquire();
                acquireAccess.acquire();
                queue.add(Thread.currentThread().getName()+": "+i++);
                acquireAccess.release();
                readSem.release();

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable consumer = ()->{
        while (true){
            try {
                readSem.acquire();
                System.out.println("Consumed :"+queue.poll());
                writeSem.release();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
        }

    };

    // single consumer and multiple producer

    public static void main(String[] args) {
        SemaphoresExample semaphoresExample = new SemaphoresExample(5);
        Thread t1 = new Thread(semaphoresExample.produce);
        Thread t2 = new Thread(semaphoresExample.consumer);
        Thread t3 = new Thread(semaphoresExample.produce);
        Thread t4 = new Thread(semaphoresExample.produce);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

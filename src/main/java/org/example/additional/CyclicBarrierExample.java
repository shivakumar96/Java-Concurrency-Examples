package org.example.additional;


/*
    The Cyclic Barrier acts as a Gate,
    waits for a set of threads to reach that point and allow them to execute only after
    reaching a specified count.
    Cyclic Barrier trips the count back to the original value
 */

import java.util.Random;
import java.util.concurrent.*;

class CyclicTask implements Runnable {
    CyclicBarrier barrier;

    public CyclicTask(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // Cyclic :
        while (true) {
            System.out.println("Entered");
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
                System.out.println("Waiting for the task to reach");
                barrier.await();
                System.out.println("Crossed Barrier");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Continuing with other task Done");
        }
    }
}
public class CyclicBarrierExample {
    public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(3);
        CyclicBarrier barrier = new CyclicBarrier(3, ()->{
            System.out.println("Action methods is called");
        });  // provide actions as runnable, called when the barrier trips
        Runnable task1 = new CyclicTask(barrier);
        Runnable task2 = new CyclicTask(barrier);
        Runnable task3 = new CyclicTask(barrier);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(task1);
        service.submit(task2);
        service.submit(task3);
        service.shutdown();
    }
}

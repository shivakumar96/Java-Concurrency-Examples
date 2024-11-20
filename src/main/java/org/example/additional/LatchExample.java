package org.example.additional;

/*
    Example of CountDown latch:
    This is used to wait by the main thread to wait until its dependent child to complete portion of tasks

 */

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable{
    CountDownLatch latch;

    public Task(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Initialization Done");
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
        System.out.println("Continuing with other task Done");

    }
}
public class LatchExample {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);
        Runnable task1 = new Task(latch);
        Runnable task2 = new Task(latch);
        Runnable task3 = new Task(latch);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(task1);
        service.submit(task2);
        service.submit(task3);
        latch.await();
        System.out.println("All task initialization is complete");
        service.shutdown();

    }
}

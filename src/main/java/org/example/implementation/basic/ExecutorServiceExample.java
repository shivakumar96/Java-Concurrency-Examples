package org.example.implementation.basic;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" done");
    }
}

public class ExecutorServiceExample {

    public static void main(String[] args) throws InterruptedException {

        // type - 1
        ExecutorService service = Executors.newFixedThreadPool(10);
        // type - 2
        ExecutorService service2 = Executors.newCachedThreadPool();

        // type 3 Scheduled executor service
        ScheduledExecutorService service3 = Executors.newScheduledThreadPool(10);

        // type 4
        ExecutorService service1 = Executors.newSingleThreadExecutor();

        service3.schedule(()->{System.out.println("Thread 1");},6, TimeUnit.SECONDS);
        service3.schedule(()->{System.out.println("Thread 2");},4, TimeUnit.SECONDS);
        service3.scheduleAtFixedRate(()->{System.out.println("Thread 3");},1, 500,TimeUnit.MILLISECONDS);


        // creates 100 tasks
//        for (int i=0; i<100;i++){
//            service2.execute(new Task());
//        }

        //service3.shutdown();
        service3.shutdown();
        //service3.awaitTermination(15,TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName());

        /*
        Lifecycle methods
         */

        //initial shutdown
        //service.shutdown();

        //will throw rejectionExecutionException
        // service.execute( new Task());

        // will return true if all the tasks are completed
        //service.isShutdown();

        // will return tru if all the tasks are completed
        //service.isTerminated();

        // block until all tasks are completed or if time out occurs
        //service.awaitTermination(10,TimeUnit.SECONDS); always called after shutdown

        // will shut down and return all queued tasks
        // List<Runnable> tasks =service.shutdownNow();


    }
}

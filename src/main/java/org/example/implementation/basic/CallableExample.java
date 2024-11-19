package org.example.implementation.basic;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;


public class CallableExample implements Callable<Integer> {

    // Callable allows you to throw the checked exception
    @Override
    public Integer call() throws Exception {
        return 1;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        //Future<?> future for runnable method.
        Future<Integer> future = service.submit(new CallableExample());

        try {
            Integer in = future.get(); // blocking operation
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        //cancel task if not submitted, has no effect if the task has already stared.
        future.cancel(true);

        // return true if the task is cancelled.
        future.isCancelled();

        //check if the task is completed, returns true even if the task is ended because of exception
        future.isDone();


        // another way of calling
        FutureTask<Integer> futureTask = new FutureTask<>(new CallableExample());
        new Thread(futureTask).start();
        try {
            Integer in = futureTask.get(); // blocking operation
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}

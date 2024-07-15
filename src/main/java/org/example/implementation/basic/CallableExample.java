package org.example.implementation.basic;

import java.util.concurrent.*;

public class CallableExample implements Callable<Integer> {
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



    }

}
